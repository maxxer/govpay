/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://www.link.it).
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.service.context.ContextThreadLocal;
import org.openspcoop2.utils.service.context.IContext;
import org.slf4j.Logger;

import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.AnagraficaManager;
import it.govpay.bd.model.Dominio;
import it.govpay.bd.model.Evento;
import it.govpay.bd.model.Fr;
import it.govpay.bd.model.Incasso;
import it.govpay.bd.model.Rendicontazione;
import it.govpay.bd.pagamento.FrBD;
import it.govpay.bd.pagamento.IncassiBD;
import it.govpay.bd.pagamento.PagamentiBD;
import it.govpay.bd.pagamento.RendicontazioniBD;
import it.govpay.bd.pagamento.VersamentiBD;
import it.govpay.bd.pagamento.filters.FrFilter;
import it.govpay.bd.pagamento.filters.IncassoFilter;
import it.govpay.core.dao.pagamenti.dto.RichiestaIncassoDTO;
import it.govpay.core.dao.pagamenti.dto.RichiestaIncassoDTOResponse;
import it.govpay.core.exceptions.GovPayException;
import it.govpay.core.exceptions.IncassiException;
import it.govpay.core.exceptions.IncassiException.FaultType;
import it.govpay.core.exceptions.NotAuthorizedException;
import it.govpay.core.utils.AvvisaturaUtils;
import it.govpay.core.utils.GovpayConfig;
import it.govpay.core.utils.IncassoUtils;
import it.govpay.model.Evento.CategoriaEvento;
import it.govpay.model.Evento.EsitoEvento;
import it.govpay.model.Evento.RuoloEvento;
import it.govpay.model.Fr.StatoFr;
import it.govpay.model.Pagamento.Stato;
import it.govpay.model.Pagamento.TipoPagamento;
import it.govpay.model.Rendicontazione.EsitoRendicontazione;
import it.govpay.model.Rendicontazione.StatoRendicontazione;
import it.govpay.model.SingoloVersamento.StatoSingoloVersamento;
import it.govpay.model.Versamento.AvvisaturaOperazione;
import it.govpay.model.Versamento.ModoAvvisatura;
import it.govpay.model.Versamento.StatoVersamento;


public class Incassi extends BasicBD {

	private static Logger log = LoggerWrapperFactory.getLogger(Incassi.class);

	public Incassi(BasicBD basicBD) {
		super(basicBD);
	}

	public RichiestaIncassoDTOResponse richiestaIncasso(RichiestaIncassoDTO richiestaIncasso) throws NotAuthorizedException, GovPayException, IncassiException {
		
		try {
			IContext ctx = ContextThreadLocal.get();
			ctx.getApplicationLogger().log("incasso.richiesta");
			
			// Validazione dati obbligatori
			boolean iuvIdFlussoSet = richiestaIncasso.getIuv() != null || richiestaIncasso.getIdFlusso() != null;
			
			if(!iuvIdFlussoSet && richiestaIncasso.getCausale() == null) {
				ctx.getApplicationLogger().log("incasso.sintassi", "causale mancante");
				throw new IncassiException(FaultType.ERRORE_SINTASSI, "Nella richiesta di incasso non e' stato specificato il campo obbligatorio causale");
			}
			
			if(!iuvIdFlussoSet && richiestaIncasso.getCausale().length() > 512) {
				ctx.getApplicationLogger().log("incasso.sintassi", "causale troppo lunga");
				throw new IncassiException(FaultType.ERRORE_SINTASSI, "Nella richiesta di incasso e' stato specificata una causale che eccede il massimo numero di caratteri consentiti (512)");
			}
			
			if(richiestaIncasso.getCodDominio() == null) {
				ctx.getApplicationLogger().log("incasso.sintassi", "dominio mancante");
				throw new IncassiException(FaultType.ERRORE_SINTASSI, "Nella richiesta di incasso non e' stato specificato il campo obbligatorio cod_dominio");
			}
			
			if(richiestaIncasso.getImporto() == null) {
				ctx.getApplicationLogger().log("incasso.sintassi", "importo mancante");
				throw new IncassiException(FaultType.ERRORE_SINTASSI, "Nella richiesta di incasso non e' stato specificato il campo obbligatorio importo");
			}
			
			// Verifica Dominio
			Dominio dominio = null;
			try {
				dominio = AnagraficaManager.getDominio(this, richiestaIncasso.getCodDominio());
			} catch (NotFoundException e) {
				ctx.getApplicationLogger().log("incasso.dominioInesistente", richiestaIncasso.getCodDominio());
				throw new IncassiException(FaultType.DOMINIO_INESISTENTE, "Il dominio " + richiestaIncasso.getCodDominio() + " indicato nella richiesta non risulta censito in anagrafica GovPay.");
			}
			
			// Verifica IbanAccredito, se indicato
			if(richiestaIncasso.getIbanAccredito() != null)
			try {
				AnagraficaManager.getIbanAccredito(this, dominio.getId(), richiestaIncasso.getIbanAccredito());
			} catch (NotFoundException e) {
				ctx.getApplicationLogger().log("incasso.ibanInesistente", richiestaIncasso.getIbanAccredito());
				throw new IncassiException(FaultType.IBAN_INESISTENTE, "Il dominio " + richiestaIncasso.getCodDominio() + " indicato nella richiesta non risulta censito in anagrafica GovPay.");
			}
			
			Long idApplicazione = null;
			Long idOperatore = null;
			
			// Verifica autorizzazione all'incasso e acquisizione applicazione chiamante
			if(richiestaIncasso.getApplicazione() != null) {
				idApplicazione = richiestaIncasso.getApplicazione().getId();
			} else if(richiestaIncasso.getOperatore() != null) {
				idOperatore = richiestaIncasso.getOperatore().getId();
			} else {
				throw new NotAuthorizedException("Utente non autorizzato al servizio di Incassi");
			} 
			
			
			// Validazione della causale
			String causale = richiestaIncasso.getCausale();
			String iuv = null;
			String idf = null;
			
			if(!iuvIdFlussoSet) {
				try {
					if(causale != null) {
						// Riversamento singolo
						iuv = IncassoUtils.getRiferimentoIncassoSingolo(causale);
						idf = IncassoUtils.getRiferimentoIncassoCumulativo(causale);
					} 
				} catch (Throwable e) {
					log.error("Riscontrato errore durante il parsing della causale",e);
				} finally {
					if(iuv == null && idf==null) {
						ctx.getApplicationLogger().log("incasso.causaleNonValida", causale);
						throw new IncassiException(FaultType.CAUSALE_NON_VALIDA, "La causale dell'operazione di incasso non e' conforme alle specifiche AgID (SACIV 1.2.1): " + causale);
					}
				} 
			} else {
				iuv = richiestaIncasso.getIuv();
				idf = richiestaIncasso.getIdFlusso();
				causale = iuv != null ? iuv : idf;
				richiestaIncasso.setCausale(causale);
			}
			
			IncassiBD incassiBD = new IncassiBD(this);
			GiornaleEventi giornaleEventi = new GiornaleEventi(this);
			
			// OVERRIDE TRN NUOVA GESTIONE
			richiestaIncasso.setTrn(iuv != null ? iuv : idf);
			RichiestaIncassoDTOResponse richiestaIncassoResponse = new RichiestaIncassoDTOResponse();
			
			// Controllo se il TRN dell'incasso e' gia registrato
			try {
				Incasso incasso = incassiBD.getIncasso(dominio.getCodDominio(), richiestaIncasso.getTrn());
				
				// Richiesta presente. Verifico che i dati accessori siano gli stessi
				if(!richiestaIncasso.getCausale().equals(incasso.getCausale())) {
					ctx.getApplicationLogger().log("incasso.sintassi", "causale");
					throw new IncassiException(FaultType.DUPLICATO, "Incasso gia' registrato con causale diversa");
				}
				if(!richiestaIncasso.getCodDominio().equals(incasso.getCodDominio())) {
					ctx.getApplicationLogger().log("incasso.sintassi", "dominio");
					throw new IncassiException(FaultType.DUPLICATO, "Incasso gia' registrato con dominio diverso");
				}
				if(richiestaIncasso.getImporto().compareTo(incasso.getImporto()) != 0) {
					ctx.getApplicationLogger().log("incasso.sintassi", "importo");
					throw new IncassiException(FaultType.DUPLICATO, "Incasso gia' registrato con importo diverso");
				}
				
				richiestaIncassoResponse.setIncasso(incasso);
				richiestaIncassoResponse.setCreated(false);
				return richiestaIncassoResponse;
			} catch(NotFoundException nfe) {
				// Incasso non registrato.
				richiestaIncassoResponse.setCreated(true);
			}
			
			// Controllo se l'idf o lo iuv sono gia' stati incassati in precedenti incassi
			IncassoFilter incassoFilter = incassiBD.newFilter();
			List<String> codDomini = new ArrayList<>();
			codDomini.add(richiestaIncasso.getCodDominio());
			incassoFilter.setCodDomini(codDomini);
			if(idf != null)
				incassoFilter.setCausale(idf);
			else
				incassoFilter.setCausale(iuv);
			List<Incasso> findAll = incassiBD.findAll(incassoFilter);
			if(findAll.size() != 0) {
				ctx.getApplicationLogger().log("incasso.causaleGiaIncassata", causale);
				if(idf != null)
					throw new IncassiException(FaultType.CAUSALE_GIA_INCASSATA, "Il flusso di rendicontazione [" + idf + "] indicato in causale risulta gia' incassato");
				else
					throw new IncassiException(FaultType.CAUSALE_GIA_INCASSATA, "Lo iuv [" + iuv + "] indicato in causale risulta gia' incassato");
			}
			
			// Sto selezionando i pagamenti per impostarli come Incassati.
			this.enableSelectForUpdate();
			this.setAutoCommit(false);
			
			List<it.govpay.bd.model.Pagamento> pagamenti = new ArrayList<>();
			
			// Riversamento singolo
			if(iuv != null) {
				PagamentiBD pagamentiBD = new PagamentiBD(this);
				try {
					it.govpay.bd.model.Pagamento pagamento = pagamentiBD.getPagamento(richiestaIncasso.getCodDominio(), iuv);
					pagamenti.add(pagamento);
				} catch (NotFoundException nfe) {
					ctx.getApplicationLogger().log("incasso.iuvNonTrovato", iuv);
					throw new IncassiException(FaultType.PAGAMENTO_NON_TROVATO, "Lo IUV " + iuv + " estratto dalla causale di incasso non identifica alcun pagamento per il creditore " + richiestaIncasso.getCodDominio());
				} catch (MultipleResultException mre) {
					ctx.getApplicationLogger().log("incasso.iuvPagamentiMultipli", iuv, richiestaIncasso.getCodDominio());
					throw new IncassiException(FaultType.PAGAMENTO_NON_IDENTIFICATO, "Lo IUV " + iuv + " estratto dalla causale di incasso identifica piu' di un pagamento per il creditore " + richiestaIncasso.getCodDominio());
				}
			}
			
			// Riversamento cumulativo
			Fr fr = null;
			if(idf != null) {
				FrBD frBD = new FrBD(this);
				try {
					// Cerco l'idf come case insensitive
					FrFilter newFilter = frBD.newFilter();
					newFilter.setCodFlusso(idf);
					List<Fr> frs = frBD.findAll(newFilter);
					
					for(Fr tmp : frs) {
						if(tmp.getCodFlusso().equalsIgnoreCase(idf))
							fr = tmp;
					}
					if(fr == null) throw new NotFoundException();
					
					if(!fr.getStato().equals(StatoFr.ACCETTATA)) {
						ctx.getApplicationLogger().log("incasso.frAnomala", idf);
						throw new IncassiException(FaultType.FR_ANOMALA, "Il flusso di rendicontazione " + idf + " identificato dalla causale di incasso risulta avere delle anomalie");
					}
					
					PagamentiBD pagamentiBD = new PagamentiBD(this);
					VersamentiBD versamentiBD = new VersamentiBD(this);
					Versamento versamentoBusiness = new Versamento(this);
					RendicontazioniBD rendicontazioniBD = new RendicontazioniBD(this);
					
					for(Rendicontazione rendicontazione : fr.getRendicontazioni(this)) {
						if(!rendicontazione.getStato().equals(StatoRendicontazione.OK)) {
							ctx.getApplicationLogger().log("incasso.frAnomala", idf);
							throw new IncassiException(FaultType.FR_ANOMALA, "Il flusso di rendicontazione " + idf + " identificato dalla causale di incasso risulta avere delle anomalie");
						}
						
						it.govpay.bd.model.Pagamento pagamento = rendicontazione.getPagamento(this);
						
						
						if(pagamento == null && rendicontazione.getEsito().equals(EsitoRendicontazione.ESEGUITO_SENZA_RPT)) {
							// Incasso di un pagamento senza RPT. Controllo se il pagamento non e' stato creato nel frattempo dall'arrivo di una RT
							
							try {
								pagamento = pagamentiBD.getPagamento(fr.getCodDominio(), rendicontazione.getIuv(), rendicontazione.getIur(), rendicontazione.getIndiceDati());
								// Pagamento gia presente. 
							} catch (NotFoundException e) {
								// Pagamento non presente. Lo inserisco 
								
								it.govpay.bd.model.Versamento versamento = null;
								try {
									// Workaround per le limitazioni in select for update. Da rimuovere quando lo iuv sara nel versamento.
									this.disableSelectForUpdate();
									versamento = versamentoBusiness.chiediVersamento(null, null, null, null, fr.getCodDominio(), rendicontazione.getIuv());
									this.enableSelectForUpdate();
									versamentiBD.getVersamento(versamento.getId());
								} catch (GovPayException gpe) {
									// Non deve accadere... la rendicontazione e' ok
									throw new IncassiException(FaultType.FR_ANOMALA, "Il versamento rendicontato [Dominio:" + fr.getCodDominio()+ " IUV:"+rendicontazione.getIuv()+"] non esiste");
								}
								
								pagamento = new it.govpay.bd.model.Pagamento();
								pagamento.setTipo(TipoPagamento.ENTRATA);
								pagamento.setStato(Stato.PAGATO_SENZA_RPT);
								pagamento.setCodDominio(fr.getCodDominio());
								pagamento.setDataAcquisizione(rendicontazione.getData());
								pagamento.setDataPagamento(rendicontazione.getData());
								pagamento.setImportoPagato(rendicontazione.getImporto());
								pagamento.setIur(rendicontazione.getIur());
								pagamento.setIuv(rendicontazione.getIuv());
								pagamento.setIndiceDati(rendicontazione.getIndiceDati() == null ? 1 : rendicontazione.getIndiceDati());
								pagamento.setSingoloVersamento(versamento.getSingoliVersamenti(this).get(0));
								rendicontazione.setPagamento(pagamento);
								pagamentiBD.insertPagamento(pagamento);
								rendicontazione.setIdPagamento(pagamento.getId());
									
								//Aggiorno lo stato del versamento:
								switch (versamento.getSingoliVersamenti(this).get(0).getStatoSingoloVersamento()) {
									case NON_ESEGUITO:
										versamentiBD.updateStatoSingoloVersamento(versamento.getSingoliVersamenti(this).get(0).getId(), StatoSingoloVersamento.ESEGUITO);
										versamentiBD.updateStatoVersamento(versamento.getId(), StatoVersamento.ESEGUITO, "Eseguito senza RPT");
										// Avvisatura
										versamentiBD.updateVersamentoOperazioneAvvisatura(versamento.getId(), AvvisaturaOperazione.DELETE.getValue()); 
										String avvisaturaDigitaleModalitaAnnullamentoAvviso = GovpayConfig.getInstance().getAvvisaturaDigitaleModalitaAnnullamentoAvviso();
										if(!avvisaturaDigitaleModalitaAnnullamentoAvviso.equals(AvvisaturaUtils.AVVISATURA_DIGITALE_MODALITA_USER_DEFINED)) {
											versamentiBD.updateVersamentoModalitaAvvisatura(versamento.getId(), avvisaturaDigitaleModalitaAnnullamentoAvviso.equals("asincrona") ? ModoAvvisatura.ASICNRONA.getValue() : ModoAvvisatura.SINCRONA.getValue());
										}
										// schedulo l'invio dell'avvisatura
										versamentiBD.updateVersamentoStatoAvvisatura(versamento.getId(), true);
										
										break;
									case ESEGUITO:
										versamento.setAnomalo(true);
										versamentiBD.updateStatoVersamento(versamento.getId(), StatoVersamento.ESEGUITO, "Pagamento duplicato");
										break;
								}
								
								versamentiBD.updateVersamento(versamento);
								
								Evento eventoNota = new Evento();
								eventoNota.setCategoriaEvento(CategoriaEvento.INTERNO);
								eventoNota.setRuoloEvento(RuoloEvento.CLIENT);
								eventoNota.setCodVersamentoEnte(versamento.getCodVersamentoEnte());
								eventoNota.setCodApplicazione(versamento.getApplicazione(this).getCodApplicazione());
								eventoNota.setEsitoEvento(EsitoEvento.OK);
								eventoNota.setDettaglioEsito("Riconciliato flusso " + fr.getCodFlusso() + " con Pagamento senza RPT [IUV: " + rendicontazione.getIuv() + " IUR:" + rendicontazione.getIur() + "].");
								eventoNota.setTipoEvento("Pagamento eseguito senza RPT");
								giornaleEventi.registraEvento(eventoNota);
							} catch (MultipleResultException e) {
								ctx.getApplicationLogger().log("incasso.frAnomala", idf);
								throw new IncassiException(FaultType.FR_ANOMALA, "La rendicontazione [Dominio:"+fr.getCodDominio()+" Iuv:" + rendicontazione.getIuv()+ " Iur:" + rendicontazione.getIur() + " Indice:" + rendicontazione.getIndiceDati() + "] non identifica univocamente un pagamento");
							}
						}
						
						//Aggiorno la FK della rendicontazione
						rendicontazione.setIdPagamento(pagamento.getId());
						rendicontazioniBD.updateRendicontazione(rendicontazione);
						
						pagamenti.add(pagamento);
					}
				} catch (NotFoundException nfe) {
					ctx.getApplicationLogger().log("incasso.idfNonTrovato", idf);
					throw new IncassiException(FaultType.IDF_NON_TROVATO, "L'identificativo " + idf + " estratto dalla causale di incasso non identifica alcun flusso di rendicontazione");
				} 
			}
			
			// Verifica stato dei pagamenti da incassare e calcolo dell'importo pagato
			BigDecimal totalePagato = BigDecimal.ZERO;
			for(it.govpay.bd.model.Pagamento pagamento : pagamenti) {
				if(Stato.INCASSATO.equals(pagamento.getStato())) {
					ctx.getApplicationLogger().log("incasso.pagamentoGiaIncassato", pagamento.getCodDominio(), pagamento.getIuv(), pagamento.getIur());
					throw new IncassiException(FaultType.PAGAMENTO_GIA_INCASSATO, "Uno dei pagamenti incassati [Dominio:" + pagamento.getCodDominio() + " Iuv:" + pagamento.getIuv() + " Iur:" + pagamento.getIur() + "] risuta gia' incassato.");
				}
				totalePagato = totalePagato.add(pagamento.getImportoPagato());
			}
			
			// Verifica importo pagato con l'incassato
			if(totalePagato.doubleValue() != richiestaIncasso.getImporto().doubleValue()) {
				ctx.getApplicationLogger().log("incasso.importoErrato", totalePagato.doubleValue() + "", richiestaIncasso.getImporto().doubleValue() + "");
				throw new IncassiException(FaultType.IMPORTO_ERRATO, "L'importo incassato [" + richiestaIncasso.getImporto() + "] non corriponde alla somma dei pagamenti [" + totalePagato.doubleValue() + "]");
			}
			
			// Inserisco l'incasso e aggiorno lo stato dei pagamenti
			try {
				it.govpay.bd.model.Incasso incasso = new it.govpay.bd.model.Incasso();
				incasso.setCausale(richiestaIncasso.getCausale());
				incasso.setCodDominio(richiestaIncasso.getCodDominio());
				incasso.setDataIncasso(new Date());
				incasso.setDataValuta(richiestaIncasso.getDataValuta());
				incasso.setDataContabile(richiestaIncasso.getDataContabile());
				incasso.setDispositivo(richiestaIncasso.getDispositivo());
				incasso.setImporto(richiestaIncasso.getImporto());
				incasso.setTrn(richiestaIncasso.getTrn());
				incasso.setIbanAccredito(richiestaIncasso.getIbanAccredito());
				incasso.setIdApplicazione(idApplicazione);
				incasso.setIdOperatore(idOperatore); 
				incasso.setSct(richiestaIncasso.getSct());
				richiestaIncassoResponse.setIncasso(incasso);
				incassiBD.insertIncasso(incasso);
				
				PagamentiBD pagamentiBD = new PagamentiBD(this);
				for(it.govpay.bd.model.Pagamento pagamento : pagamenti) {
					pagamento.setStato(Stato.INCASSATO);
					pagamento.setIncasso(incasso);
					pagamentiBD.updatePagamento(pagamento);
				}
				
				// se e' un incasso cumulativo collego il flusso all'incasso
				if(fr != null) {
					FrBD frBD = new FrBD(this);
					frBD.updateIdIncasso(fr.getId(), incasso.getId());
				}
				
				this.commit();
			} catch(Exception e) {
				this.rollback();
				throw new GovPayException(e);
			} finally {
				this.setAutoCommit(true);
			}
			
			return richiestaIncassoResponse;
		} catch (ServiceException e) {
			throw new GovPayException(e);
		} catch (UtilsException e) {
			throw new GovPayException(e);
		} finally {
			try {
				this.disableSelectForUpdate();
			} catch (ServiceException e) {}
		}
	}
}


