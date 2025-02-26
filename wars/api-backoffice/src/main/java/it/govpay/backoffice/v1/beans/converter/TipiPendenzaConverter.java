package it.govpay.backoffice.v1.beans.converter;

import org.apache.commons.lang.ArrayUtils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.jaxrs.RawObject;
import org.openspcoop2.utils.json.ValidationException;
import org.springframework.security.core.Authentication;

import it.govpay.backoffice.v1.beans.TipoPendenza;
import it.govpay.backoffice.v1.beans.TipoPendenzaForm;
import it.govpay.backoffice.v1.beans.TipoPendenzaIndex;
import it.govpay.backoffice.v1.beans.TipoPendenzaPost;
import it.govpay.backoffice.v1.beans.TipoPendenzaPromemoria;
import it.govpay.backoffice.v1.beans.TipoPendenzaTipologia;
import it.govpay.backoffice.v1.beans.TipoPendenzaTrasformazione;
import it.govpay.backoffice.v1.beans.TipoPendenzaTrasformazione.TipoEnum;
import it.govpay.backoffice.v1.beans.TracciatoCsv;
import it.govpay.bd.model.TipoVersamentoDominio;
import it.govpay.core.dao.anagrafica.dto.PutTipoPendenzaDTO;
import it.govpay.core.utils.rawutils.ConverterUtils;

public class TipiPendenzaConverter {

	public static PutTipoPendenzaDTO getPutTipoPendenzaDTO(TipoPendenzaPost entrataPost, String idTipoPendenza, Authentication user) throws ServiceException, ValidationException {
		PutTipoPendenzaDTO entrataDTO = new PutTipoPendenzaDTO(user);
		
		it.govpay.model.TipoVersamento tipoVersamento = new it.govpay.model.TipoVersamento();
		
		if(entrataPost.getCodificaIUV()!=null)
			tipoVersamento.setCodificaIuvDefault(entrataPost.getCodificaIUV().toString());
		tipoVersamento.setCodTipoVersamento(idTipoPendenza);
		tipoVersamento.setDescrizione(entrataPost.getDescrizione());
		if(entrataPost.getTipo() != null) {
			
			// valore tipo contabilita non valido
			if(TipoPendenzaTipologia.fromValue(entrataPost.getTipo()) == null) {
				throw new ValidationException("Codifica inesistente per tipo. Valore fornito [" + entrataPost.getTipo() + "] valori possibili " + ArrayUtils.toString(TipoPendenzaTipologia.values()));
			}
			
			entrataPost.setTipoEnum(TipoPendenzaTipologia.fromValue(entrataPost.getTipo()));

			switch (entrataPost.getTipoEnum()) {
			case DOVUTO:
				tipoVersamento.setTipoDefault(it.govpay.model.TipoVersamento.Tipo.DOVUTO);
				break;
			case SPONTANEO:
				tipoVersamento.setTipoDefault(it.govpay.model.TipoVersamento.Tipo.SPONTANEO);
				break;
			}
		}

		entrataDTO.setCodTipoVersamento(idTipoPendenza);
		entrataDTO.setTipoVersamento(tipoVersamento);
		
		tipoVersamento.setPagaTerziDefault(entrataPost.PagaTerzi());
		tipoVersamento.setAbilitatoDefault(entrataPost.Abilitato());
		
		if(entrataPost.getForm() != null) {
			Object definizione = entrataPost.getForm().getDefinizione();
//			if(definizione instanceof String)
//				tipoVersamento.setFormDefinizioneDefault((String) definizione);
//			else
				tipoVersamento.setFormDefinizioneDefault(ConverterUtils.toJSON(definizione,null));
			tipoVersamento.setFormTipoDefault(entrataPost.getForm().getTipo());
		}
		
		if(entrataPost.getPromemoriaAvviso() != null && entrataPost.getPromemoriaAvviso().getTipo() != null
				&& entrataPost.getPromemoriaAvviso().getMessaggio() != null
				&& entrataPost.getPromemoriaAvviso().getOggetto() != null) {
			tipoVersamento.setPromemoriaAvvisoTipoDefault(entrataPost.getPromemoriaAvviso().getTipo());
			tipoVersamento.setPromemoriaAvvisoMessaggioDefault(ConverterUtils.toJSON(entrataPost.getPromemoriaAvviso().getMessaggio(),null));
			tipoVersamento.setPromemoriaAvvisoOggettoDefault(ConverterUtils.toJSON(entrataPost.getPromemoriaAvviso().getOggetto(),null));
			tipoVersamento.setPromemoriaAvvisoPdfDefault(entrataPost.getPromemoriaAvviso().AllegaPdf());
		}
		
		if(entrataPost.getPromemoriaRicevuta() != null && entrataPost.getPromemoriaRicevuta().getTipo() != null
				&& entrataPost.getPromemoriaRicevuta().getMessaggio() != null
				&& entrataPost.getPromemoriaRicevuta().getOggetto() != null) {
			tipoVersamento.setPromemoriaRicevutaTipoDefault(entrataPost.getPromemoriaRicevuta().getTipo());
			tipoVersamento.setPromemoriaRicevutaMessaggioDefault(ConverterUtils.toJSON(entrataPost.getPromemoriaRicevuta().getMessaggio(),null));
			tipoVersamento.setPromemoriaRicevutaOggettoDefault(ConverterUtils.toJSON(entrataPost.getPromemoriaRicevuta().getOggetto(),null));
			tipoVersamento.setPromemoriaRicevutaPdfDefault(entrataPost.getPromemoriaRicevuta().AllegaPdf());
		}
		
		if(entrataPost.getTrasformazione() != null) {
			if(entrataPost.getTrasformazione().getTipo() != null) {
				// valore tipo contabilita non valido
				if(TipoEnum.fromValue(entrataPost.getTrasformazione().getTipo()) == null) {
					throw new ValidationException("Codifica inesistente per tipo trasformazione. Valore fornito [" + entrataPost.getTrasformazione().getTipo() + "] valori possibili " + ArrayUtils.toString(TipoEnum.values()));
				}
			}
			
			Object definizione = entrataPost.getTrasformazione().getDefinizione();
//			if(definizione instanceof String)
//				tipoVersamento.setTrasformazioneDefinizioneDefault((String) definizione);
//			else
				tipoVersamento.setTrasformazioneDefinizioneDefault(ConverterUtils.toJSON(definizione,null));
			tipoVersamento.setTrasformazioneTipoDefault(entrataPost.getTrasformazione().getTipo());
		}
		if(entrataPost.getValidazione() != null)
			tipoVersamento.setValidazioneDefinizioneDefault(ConverterUtils.toJSON(entrataPost.getValidazione(),null));
		
		if(entrataPost.getInoltro() != null)
			tipoVersamento.setCodApplicazioneDefault(entrataPost.getInoltro());
		
		if(entrataPost.getVisualizzazione() != null)
			tipoVersamento.setVisualizzazioneDefinizioneDefault(ConverterUtils.toJSON(entrataPost.getVisualizzazione(),null));
		
		if(entrataPost.getTracciatoCsv() != null && entrataPost.getTracciatoCsv().getResponseHeader() != null
				&& entrataPost.getTracciatoCsv().getFreemarkerRequest() != null
				&& entrataPost.getTracciatoCsv().getFreemarkerResponse() != null) {
			tipoVersamento.setTracciatoCsvHeaderRispostaDefault(entrataPost.getTracciatoCsv().getResponseHeader());
			tipoVersamento.setTracciatoCsvFreemarkerRichiestaDefault(ConverterUtils.toJSON(entrataPost.getTracciatoCsv().getFreemarkerRequest(),null));
			tipoVersamento.setTracciatoCsvFreemarkerRispostaDefault(ConverterUtils.toJSON(entrataPost.getTracciatoCsv().getFreemarkerResponse(),null));
		}
		
		return entrataDTO;		
	}
	
	public static TipoPendenza toTipoPendenzaRsModel(it.govpay.model.TipoVersamento tipoVersamento) {
		TipoPendenza rsModel = new TipoPendenza();
		
		rsModel.descrizione(tipoVersamento.getDescrizione())
		.idTipoPendenza(tipoVersamento.getCodTipoVersamento()).codificaIUV(tipoVersamento.getCodificaIuvDefault())
		.abilitato(tipoVersamento.isAbilitatoDefault());
		
		if(tipoVersamento.getTipoDefault() != null) {
			switch (tipoVersamento.getTipoDefault()) {
			case DOVUTO:
				rsModel.setTipo(it.govpay.backoffice.v1.beans.TipoPendenzaTipologia.DOVUTO);
				break;
			case SPONTANEO:
				rsModel.setTipo(it.govpay.backoffice.v1.beans.TipoPendenzaTipologia.SPONTANEO);
				break;
			}
		}
		
		rsModel.setPagaTerzi(tipoVersamento.getPagaTerziDefault());
		
		if(tipoVersamento.getFormDefinizioneDefault() != null && tipoVersamento.getFormTipoDefault() != null) {
			TipoPendenzaForm form = new TipoPendenzaForm();
			form.setTipo(tipoVersamento.getFormTipoDefault());
			form.setDefinizione(new RawObject(tipoVersamento.getFormDefinizioneDefault())); 
			rsModel.setForm(form);
		}
		
		if(tipoVersamento.getPromemoriaAvvisoMessaggioDefault() != null && tipoVersamento.getPromemoriaAvvisoOggettoDefault() != null
				&& tipoVersamento.getPromemoriaAvvisoTipoDefault() != null) {
			TipoPendenzaPromemoria promemoria = new TipoPendenzaPromemoria();
			promemoria.setOggetto(new RawObject(tipoVersamento.getPromemoriaAvvisoOggettoDefault()));
			promemoria.setMessaggio(new RawObject(tipoVersamento.getPromemoriaAvvisoMessaggioDefault()));
			promemoria.setAllegaPdf(tipoVersamento.getPromemoriaAvvisoPdfDefault());
			promemoria.setTipo(tipoVersamento.getPromemoriaAvvisoTipoDefault());
			rsModel.setPromemoriaAvviso(promemoria);
		}
		
		if(tipoVersamento.getPromemoriaRicevutaMessaggioDefault() != null && tipoVersamento.getPromemoriaRicevutaOggettoDefault() != null
				&& tipoVersamento.getPromemoriaRicevutaTipoDefault() != null) {
			TipoPendenzaPromemoria promemoria = new TipoPendenzaPromemoria();
			promemoria.setOggetto(new RawObject(tipoVersamento.getPromemoriaRicevutaOggettoDefault()));
			promemoria.setMessaggio(new RawObject(tipoVersamento.getPromemoriaRicevutaMessaggioDefault()));
			promemoria.setAllegaPdf(tipoVersamento.getPromemoriaRicevutaPdfDefault());
			promemoria.setTipo(tipoVersamento.getPromemoriaRicevutaTipoDefault());
			rsModel.setPromemoriaRicevuta(promemoria);
		}
		
		if(tipoVersamento.getTrasformazioneTipoDefault() != null && tipoVersamento.getTrasformazioneDefinizioneDefault() != null) {
			TipoPendenzaTrasformazione trasformazione  = new TipoPendenzaTrasformazione();
			trasformazione.setTipo(tipoVersamento.getTrasformazioneTipoDefault());
			trasformazione.setDefinizione(new RawObject(tipoVersamento.getTrasformazioneDefinizioneDefault())); 
			rsModel.setTrasformazione(trasformazione);
		}
		if(tipoVersamento.getValidazioneDefinizioneDefault() != null)
			rsModel.setValidazione(new RawObject(tipoVersamento.getValidazioneDefinizioneDefault()));
		
		rsModel.setInoltro(tipoVersamento.getCodApplicazioneDefault());
		
		if(tipoVersamento.getVisualizzazioneDefinizioneDefault() != null)
			rsModel.setVisualizzazione(new RawObject(tipoVersamento.getVisualizzazioneDefinizioneDefault()));
		
		if(tipoVersamento.getTracciatoCsvHeaderRispostaDefault() != null && tipoVersamento.getTracciatoCsvFreemarkerRichiestaDefault() != null
				&& tipoVersamento.getTracciatoCsvFreemarkerRispostaDefault() != null) {
			TracciatoCsv tracciatoCsv = new TracciatoCsv();
			tracciatoCsv.setResponseHeader(tipoVersamento.getTracciatoCsvHeaderRispostaDefault());
			tracciatoCsv.setFreemarkerRequest(new RawObject(tipoVersamento.getTracciatoCsvFreemarkerRichiestaDefault()));
			tracciatoCsv.setFreemarkerResponse(new RawObject(tipoVersamento.getTracciatoCsvFreemarkerRispostaDefault()));
			rsModel.setTracciatoCsv(tracciatoCsv);
		}
		
		return rsModel;
	}
	
	public static TipoPendenzaIndex toTipoPendenzaRsModelIndex(TipoVersamentoDominio tipoVersamentoDominio) {
		TipoPendenzaIndex rsModel = new TipoPendenzaIndex();
		
		rsModel.descrizione(tipoVersamentoDominio.getDescrizione())
		.idTipoPendenza(tipoVersamentoDominio.getCodTipoVersamento());
		
		if(tipoVersamentoDominio.getTipo() != null) {
			switch (tipoVersamentoDominio.getTipo()) {
			case DOVUTO:
				rsModel.setTipo(it.govpay.backoffice.v1.beans.TipoPendenzaTipologia.DOVUTO);
				break;
			case SPONTANEO:
				rsModel.setTipo(it.govpay.backoffice.v1.beans.TipoPendenzaTipologia.SPONTANEO);
				break;
			}
		}
		
		rsModel.setVisualizzazione(new RawObject(tipoVersamentoDominio.getVisualizzazioneDefinizione()));
		
		return rsModel;
	}
}
