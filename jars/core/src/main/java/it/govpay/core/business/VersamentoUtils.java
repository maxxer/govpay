/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://www.link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.govpay.core.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.json.ValidationException;

import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.AnagraficaManager;
import it.govpay.bd.model.SingoloVersamento;
import it.govpay.bd.model.Versamento;
import it.govpay.core.autorizzazione.AuthorizationManager;
import it.govpay.core.beans.Anagrafica;
import it.govpay.core.beans.EsitoOperazione;
import it.govpay.core.exceptions.GovPayException;
import it.govpay.core.utils.GovpayConfig;
import it.govpay.model.Acl.Diritti;
import it.govpay.model.Acl.Servizio;
import it.govpay.model.Anagrafica.TIPO;
import it.govpay.bd.model.Applicazione;
import it.govpay.bd.model.Dominio;
import it.govpay.model.Iuv.TipoIUV;
import it.govpay.model.SingoloVersamento.StatoSingoloVersamento;
import it.govpay.model.SingoloVersamento.TipoBollo;
import it.govpay.model.Tributo;
import it.govpay.model.Tributo.TipoContabilita;
import it.govpay.model.Versamento.AvvisaturaOperazione;
import it.govpay.model.Versamento.CausaleSemplice;
import it.govpay.model.Versamento.ModoAvvisatura;
import it.govpay.model.Versamento.StatoVersamento;

public class VersamentoUtils {
	
	public static Versamento toVersamentoModel(it.govpay.core.dao.commons.Versamento versamento, BasicBD bd) throws ServiceException, GovPayException, ValidationException { 
		Versamento model = new Versamento();
		model.setAggiornabile(versamento.isAggiornabile() == null ? true : versamento.isAggiornabile());
		model.setAnagraficaDebitore(toAnagraficaModel(versamento.getDebitore()));
		
		CausaleSemplice causale = model.new CausaleSemplice();
		causale.setCausale(versamento.getCausale());
		model.setCausaleVersamento(causale);
		model.setDatiAllegati(versamento.getDatiAllegati()); 
		model.setCodAnnoTributario(versamento.getAnnoTributario());
		model.setCodBundlekey(versamento.getBundlekey());
		model.setCodLotto(versamento.getCodDebito()); 
		model.setCodVersamentoEnte(versamento.getCodVersamentoEnte());
		model.setDataCreazione(versamento.getDataCaricamento());
		model.setDataScadenza(versamento.getDataScadenza());
		model.setDataValidita(versamento.getDataValidita());
		model.setDataUltimoAggiornamento(new Date());
		model.setDescrizioneStato(null);
		model.setNome(versamento.getNome());
		
		if(versamento.getAvvisaturaAbilitata() != null) {
			model.setAvvisaturaAbilitata(versamento.getAvvisaturaAbilitata().booleanValue());
		} else {
			// imposto il default di sistema
			model.setAvvisaturaAbilitata(GovpayConfig.getInstance().isAvvisaturaDigitaleEnabled());
		}
		
		if(versamento.getModoAvvisatura() != null) {
			model.setAvvisaturaModalita(versamento.getModoAvvisatura());
		} else {
			// imposto il default
			model.setAvvisaturaModalita(ModoAvvisatura.ASICNRONA.getValue()); 
		}
		
		model.setId(null);
		try {
			model.setApplicazione(versamento.getCodApplicazione(), bd);
		} catch (NotFoundException e) {
			throw new GovPayException(EsitoOperazione.APP_000, versamento.getCodApplicazione());
		}
		
		if(!model.getApplicazione(bd).getUtenza().isAbilitato())
			throw new GovPayException(EsitoOperazione.APP_001, versamento.getCodApplicazione());
		
		Dominio dominio = null;
		try {
			dominio = AnagraficaManager.getDominio(bd, versamento.getCodDominio());
			model.setIdDominio(dominio.getId()); 
		} catch (NotFoundException e) {
			throw new GovPayException(EsitoOperazione.DOM_000, versamento.getCodDominio());
		}
		
		if(!dominio.isAbilitato())
			throw new GovPayException(EsitoOperazione.DOM_001, dominio.getCodDominio());
		
		try {
			String codUnitaOperativa = (versamento.getCodUnitaOperativa() == null) ? it.govpay.model.Dominio.EC : versamento.getCodUnitaOperativa();
			model.setUo(dominio.getId(), codUnitaOperativa, bd);
		} catch (NotFoundException e) {
			throw new GovPayException(EsitoOperazione.UOP_000, versamento.getCodUnitaOperativa(), versamento.getCodDominio());
		}
		
		if(!model.getUo(bd).isAbilitato())
			throw new GovPayException(EsitoOperazione.UOP_001, versamento.getCodUnitaOperativa(), versamento.getCodDominio());
		
		model.setImportoTotale(versamento.getImportoTotale());
		model.setStatoVersamento(StatoVersamento.NON_ESEGUITO);
		
		// in un versamento multivoce non si puo' passare il numero avviso
		if(versamento.getSingoloVersamento().size() > 1 && StringUtils.isNotEmpty(versamento.getNumeroAvviso())) {
			throw new GovPayException(EsitoOperazione.VER_031);
		}
		
		int index = 1;
		for(it.govpay.core.dao.commons.Versamento.SingoloVersamento singoloVersamento : versamento.getSingoloVersamento()) {
			model.addSingoloVersamento(toSingoloVersamentoModel(model, singoloVersamento, index++ ,bd));
		}
		
		model.setTassonomia(versamento.getTassonomia());
		model.setTassonomiaAvviso(versamento.getTassonomiaAvviso());
		
		model.setIncasso(versamento.getIncasso());
		model.setAnomalie(versamento.getAnomalie()); 
		model.setNumeroAvviso(versamento.getNumeroAvviso());	

		if(versamento.getNumeroAvviso() != null) {
			String iuvFromNumeroAvviso = it.govpay.core.utils.VersamentoUtils.getIuvFromNumeroAvviso(versamento.getNumeroAvviso(),dominio.getCodDominio(),dominio.getStazione().getCodStazione(),
					dominio.getStazione().getApplicationCode(), dominio.getSegregationCode());
			
			// check sulla validita' dello iuv
			Iuv iuvBD  = new Iuv(bd);
			TipoIUV tipo = iuvBD.getTipoIUV(iuvFromNumeroAvviso);
			try {
				iuvBD.checkIUV(dominio, iuvFromNumeroAvviso, tipo );
			}catch(UtilsException e) {
				throw new GovPayException(e);
			}
			
			model.setIuvVersamento(iuvFromNumeroAvviso);
			model.setIuvProposto(iuvFromNumeroAvviso); 
			
//			if(versamento.getIuv().startsWith("0")) {
//				model.setIuvVersamento(versamento.getIuv().substring(1));
//			} else {
//				model.setIuvVersamento(versamento.getIuv().substring(3));
//			}
//			model.setNumeroAvviso(versamento.getIuv());
			model.setAvvisaturaOperazione(AvvisaturaOperazione.CREATE.getValue());
			model.setAvvisaturaDaInviare(true);
		}
		return model;
	}

	
	public static SingoloVersamento toSingoloVersamentoModel(Versamento versamento, it.govpay.core.dao.commons.Versamento.SingoloVersamento singoloVersamento, int index, BasicBD bd) throws ServiceException, GovPayException, ValidationException {
		SingoloVersamento model = new SingoloVersamento();
		model.setVersamento(versamento);
		model.setCodSingoloVersamentoEnte(singoloVersamento.getCodSingoloVersamentoEnte());
		model.setIndiceDati(index);
		model.setId(null);
		model.setIdVersamento(0);
		model.setImportoSingoloVersamento(singoloVersamento.getImporto());
		model.setStatoSingoloVersamento(StatoSingoloVersamento.NON_ESEGUITO);
		model.setDescrizione(singoloVersamento.getDescrizione()); 
		model.setDatiAllegati(singoloVersamento.getDatiAllegati()); 
		Dominio dominio = versamento.getUo(bd).getDominio(bd);
		if(singoloVersamento.getBolloTelematico() != null) {
			try {
				model.setTributo(Tributo.BOLLOT, bd);
			} catch (NotFoundException e) {
				throw new GovPayException(EsitoOperazione.TRB_000, dominio.getCodDominio(), Tributo.BOLLOT);
			}
			
			if(model.getTributo(bd)!= null) {
				if(!model.getTributo(bd).isAbilitato())
					throw new GovPayException(EsitoOperazione.TRB_001, dominio.getCodDominio(), Tributo.BOLLOT);
			}
			
			model.setHashDocumento(singoloVersamento.getBolloTelematico().getHash());
			model.setProvinciaResidenza(singoloVersamento.getBolloTelematico().getProvincia());
			try {
				model.setTipoBollo(TipoBollo.toEnum(singoloVersamento.getBolloTelematico().getTipo()));
			} catch (ServiceException e) {
				throw new ValidationException(e.getMessage());
			}
		} 
		
		List<Diritti> diritti = new ArrayList<>(); // TODO controllare quale diritto serve in questa fase
		diritti.add(Diritti.SCRITTURA);
		diritti.add(Diritti.ESECUZIONE);
		Applicazione applicazione = versamento.getApplicazione(bd);
		if(singoloVersamento.getCodTributo() != null) {
			try {
				model.setTributo(singoloVersamento.getCodTributo(), bd);
			} catch (NotFoundException e) {
				throw new GovPayException(EsitoOperazione.TRB_000, dominio.getCodDominio(), singoloVersamento.getCodTributo());
			}
			
			if(model.getTributo(bd)!= null) {
				if(!model.getTributo(bd).isAbilitato())
					throw new GovPayException(EsitoOperazione.TRB_001, dominio.getCodDominio(), singoloVersamento.getCodTributo());
			}
			
			if(!applicazione.isTrusted() && !AuthorizationManager.isAuthorized(applicazione.getUtenza(), applicazione.getUtenza().getTipoUtenza(), Servizio.PAGAMENTI_E_PENDENZE, dominio.getCodDominio(), singoloVersamento.getCodTributo(),diritti)) {
				throw new GovPayException(EsitoOperazione.VER_022, dominio.getCodDominio(), singoloVersamento.getCodTributo());
			}
		}
		
		if(singoloVersamento.getTributo() != null) {

			if(!applicazione.isTrusted())
				throw new GovPayException(EsitoOperazione.VER_019);
			
			if(!AuthorizationManager.isAuthorized(applicazione.getUtenza(), applicazione.getUtenza().getTipoUtenza(), Servizio.PAGAMENTI_E_PENDENZE, dominio.getCodDominio(), null, diritti))
				throw new GovPayException(EsitoOperazione.VER_021);
			
			try {
				model.setIbanAccredito(AnagraficaManager.getIbanAccredito(bd, dominio.getId(), singoloVersamento.getTributo().getIbanAccredito()));
				if(singoloVersamento.getTributo().getIbanAppoggio() != null)
					model.setIbanAppoggio(AnagraficaManager.getIbanAccredito(bd, dominio.getId(), singoloVersamento.getTributo().getIbanAppoggio()));
				model.setTipoContabilita(TipoContabilita.valueOf(singoloVersamento.getTributo().getTipoContabilita().toString()));
				model.setCodContabilita(singoloVersamento.getTributo().getCodContabilita());
				
				if(!model.getIbanAccredito(bd).isAbilitato())
					throw new GovPayException(EsitoOperazione.VER_032, dominio.getCodDominio(), singoloVersamento.getTributo().getIbanAccredito());
			} catch (NotFoundException e) {
				throw new GovPayException(EsitoOperazione.VER_020, dominio.getCodDominio(), singoloVersamento.getTributo().getIbanAccredito());
			}
		}
		
		return model;
	}
	
	public static it.govpay.model.Anagrafica toAnagraficaModel(Anagrafica anagrafica) {
		if(anagrafica == null) return null;
		it.govpay.model.Anagrafica anagraficaModel = new it.govpay.model.Anagrafica();
		anagraficaModel.setCap(anagrafica.getCap());
		anagraficaModel.setCellulare(anagrafica.getCellulare());
		anagraficaModel.setCivico(anagrafica.getCivico());
		anagraficaModel.setCodUnivoco(anagrafica.getCodUnivoco());
		anagraficaModel.setEmail(anagrafica.getEmail());
		anagraficaModel.setFax(anagrafica.getFax());
		anagraficaModel.setIndirizzo(anagrafica.getIndirizzo());
		anagraficaModel.setLocalita(anagrafica.getLocalita());
		anagraficaModel.setNazione(anagrafica.getNazione());
		anagraficaModel.setProvincia(anagrafica.getProvincia());
		anagraficaModel.setRagioneSociale(anagrafica.getRagioneSociale());
		anagraficaModel.setTelefono(anagrafica.getTelefono());
		anagraficaModel.setTipo(TIPO.valueOf(anagrafica.getTipo()));
		return anagraficaModel;
	}
	
	public static it.govpay.model.Anagrafica toAnagraficaModel(it.govpay.core.dao.commons.Anagrafica anagrafica) {
		if(anagrafica == null) return null;
		it.govpay.model.Anagrafica anagraficaModel = new it.govpay.model.Anagrafica();
		
		anagraficaModel.setCap(anagrafica.getCap());
		anagraficaModel.setCellulare(anagrafica.getCellulare());
		anagraficaModel.setCivico(anagrafica.getCivico());
		anagraficaModel.setCodUnivoco(anagrafica.getCodUnivoco());
		anagraficaModel.setEmail(anagrafica.getEmail());
		anagraficaModel.setFax(anagrafica.getFax());
		anagraficaModel.setIndirizzo(anagrafica.getIndirizzo());
		anagraficaModel.setLocalita(anagrafica.getLocalita());
		anagraficaModel.setNazione(anagrafica.getNazione());
		anagraficaModel.setProvincia(anagrafica.getProvincia());
		anagraficaModel.setRagioneSociale(anagrafica.getRagioneSociale());
		anagraficaModel.setTelefono(anagrafica.getTelefono());
		anagraficaModel.setTipo(TIPO.valueOf(anagrafica.getTipo()));
		return anagraficaModel;
	}
}
