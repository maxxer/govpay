package it.govpay.pagamento.v3.api.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.openspcoop2.utils.service.BaseImpl;
import org.openspcoop2.utils.service.context.IContext;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;

import it.govpay.core.dao.anagrafica.dto.BasicFindRequestDTO;
import it.govpay.core.dao.pagamenti.PagamentiPortaleDAO;
import it.govpay.core.dao.pagamenti.dto.LeggiPagamentoPortaleDTO;
import it.govpay.core.dao.pagamenti.dto.LeggiPagamentoPortaleDTOResponse;
import it.govpay.core.dao.pagamenti.dto.ListaPagamentiPortaleDTO;
import it.govpay.core.dao.pagamenti.dto.ListaPagamentiPortaleDTOResponse;
import it.govpay.core.dao.pagamenti.dto.PagamentiPortaleDTO;
import it.govpay.core.dao.pagamenti.dto.PagamentiPortaleDTOResponse;
import it.govpay.exception.WebApplicationExceptionMapper;
import it.govpay.model.Utenza.TIPO_UTENZA;
import it.govpay.pagamento.utils.validazione.semantica.NuovoPagamentoValidator;
import it.govpay.rs.v3.acl.Acl;
import it.govpay.rs.v3.acl.AuthorizationRules;
import it.govpay.rs.v3.acl.impl.TipoUtenzaOnlyAcl;
import it.govpay.pagamento.v3.api.PagamentiApi;
import it.govpay.pagamento.v3.beans.ModalitaAvvisaturaDigitale;
import it.govpay.pagamento.v3.beans.NuovoPagamento;
import it.govpay.pagamento.v3.beans.Pagamenti;
import it.govpay.pagamento.v3.beans.Pagamento;
import it.govpay.pagamento.v3.beans.PagamentoCreato;
import it.govpay.pagamento.v3.beans.StatoPagamento;
import it.govpay.pagamento.v3.beans.converter.PagamentiConverter;

/**
 * GovPay - API Pagamento
 *
 * <p>No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 */
public class PagamentiApiServiceImpl extends BaseImpl implements PagamentiApi {

	public static UriBuilder basePath = UriBuilder.fromPath("/pagamenti");

	public PagamentiApiServiceImpl(){
		super(org.slf4j.LoggerFactory.getLogger(PagamentiApiServiceImpl.class));
	}

	private AuthorizationRules getAuthorizationRules() throws Exception{
		AuthorizationRules ac = new AuthorizationRules();

		/*
		 * Utenti anonimi possono chiamare:
		 * - addPagamento - per avviare un pagamento
		 * - getPagamentoByIdSession - per verificarne lo stato
		 * - getPagamento - per verificarne lo stato
		 */
		{
			TIPO_UTENZA[] tipiUtenza = { TIPO_UTENZA.ANONIMO };

			Map<HttpRequestMethod, String[]> resources = new HashMap<HttpRequestMethod, String[]>();
			{
				String[] location = { "/pagamenti" };
				resources.put(HttpRequestMethod.POST, location);
			}
			{
				String[] location = { "/pagamenti/byIdSession/{idSession}" };
				resources.put(HttpRequestMethod.GET, location);
			}
			{
				String[] location = { "/pagamenti/{id}" };
				resources.put(HttpRequestMethod.GET, location);
			}

			Acl acl = new TipoUtenzaOnlyAcl(tipiUtenza, resources);
			ac.addAcl(acl);
		}
		/*
		 * Utenti CITTADINO e APPLICAZIONE possono chiamare tutte le operazioni:
		 */
		{
			TIPO_UTENZA[] tipiUtenza = { TIPO_UTENZA.CITTADINO, TIPO_UTENZA.APPLICAZIONE };
			Acl acl = new TipoUtenzaOnlyAcl(tipiUtenza);
			ac.addAcl(acl);
		}

		return ac;
	}

	/**
	 * Avvio di un pagamento di pendenze
	 *
	 * L&#x27;operazione consente di avviare una sessione di pagamento per una o più pendenze. Le pendenze presenti in posizione debitoria possono essere riferite tramite identificativo della pendenza del gestionale proprietario  (_idA2A_ e _idPendenza_) oppure con gli estremi dell&#x27;avviso (_idDominio_  e _iuv_). Nel caso invece di pendenze spontanee, è possibile fornire direttamente i dati costitutivi.
	 *
	 */
	@Override
	public PagamentoCreato addPagamento(NuovoPagamento body, String idSessionePortale, Boolean avvisaturaDigitale,	ModalitaAvvisaturaDigitale modalitaAvvisaturaDigitale) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     
			getAuthorizationRules().authorize(context);
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			String idSession = context.getTransactionId().replace("-", "");
			PagamentiPortaleDTO pagamentiPortaleDTO = PagamentiConverter.getPagamentiPortaleDTO(body, context.getAuthentication(),idSession, idSessionePortale, avvisaturaDigitale,modalitaAvvisaturaDigitale);
			
			PagamentiConverter.controlloUtenzaVersante(pagamentiPortaleDTO, context.getAuthentication());
			
			new NuovoPagamentoValidator().valida(pagamentiPortaleDTO);
			
			PagamentiPortaleDAO pagamentiPortaleDAO = new PagamentiPortaleDAO(); 
			
			PagamentiPortaleDTOResponse pagamentiPortaleDTOResponse = pagamentiPortaleDAO.inserisciPagamenti(pagamentiPortaleDTO);
						
			PagamentoCreato responseOk = PagamentiConverter.getPagamentiPortaleResponseOk(pagamentiPortaleDTOResponse);

			context.getLogger().info("Invocazione completata con successo");
			return responseOk;

		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw WebApplicationExceptionMapper.handleException(e);
//			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
	}

	/**
	 * Lista dei pagamenti
	 *
	 */
	@Override
	public Pagamenti findPagamenti(Integer offset, Integer limit, String fields, String sort, String idSessionePortale, String idSessionePsp, String idDebitore, StatoPagamento statoPagamento) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     
			getAuthorizationRules().authorize(context);
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			/* default values */
			if(offset == null || offset < 0) offset = 0;
			if(limit == null || limit < 0 || limit > 100) limit = BasicFindRequestDTO.DEFAULT_LIMIT;

			ListaPagamentiPortaleDTO listaPagamentiPortaleDTO = new ListaPagamentiPortaleDTO(context.getAuthentication());
			listaPagamentiPortaleDTO.setOffset(offset);
			listaPagamentiPortaleDTO.setLimit(limit);
			if(statoPagamento != null)
				listaPagamentiPortaleDTO.setStato(statoPagamento.name());
			listaPagamentiPortaleDTO.setIdSessionePortale(idSessionePortale);
			listaPagamentiPortaleDTO.setIdSessionePsp(idSessionePsp);
			listaPagamentiPortaleDTO.setVersante(idDebitore);

			listaPagamentiPortaleDTO.setOrderBy(sort);

			// INIT DAO

			PagamentiPortaleDAO pagamentiPortaleDAO = new PagamentiPortaleDAO();

			// CHIAMATA AL DAO

			ListaPagamentiPortaleDTOResponse pagamentoPortaleDTOResponse = pagamentiPortaleDAO.listaPagamentiPortale(listaPagamentiPortaleDTO);

			// CONVERT TO JSON DELLA RISPOSTA

			Pagamenti pagamenti = PagamentiConverter.toRsModel(pagamentoPortaleDTOResponse.getResults(),context.getUriInfo(), offset, limit, pagamentoPortaleDTOResponse.getTotalResults());
			context.getLogger().info("Invocazione completata con successo");
			
			return pagamenti;

		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw WebApplicationExceptionMapper.handleException(e);
//			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
	}

	/**
	 * Dettaglio di un pagamento
	 *
	 */
	@Override
	public Pagamento getPagamento(String id) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     
			getAuthorizationRules().authorize(context);
			context.getLogger().debug("Autorizzazione completata con successo");     

			LeggiPagamentoPortaleDTO leggiPagamentoPortaleDTO = new LeggiPagamentoPortaleDTO(context.getAuthentication());
			leggiPagamentoPortaleDTO.setId(id);
			leggiPagamentoPortaleDTO.setRisolviLink(true); 
			
			PagamentiPortaleDAO pagamentiPortaleDAO = new PagamentiPortaleDAO(); 
			
			LeggiPagamentoPortaleDTOResponse pagamentoPortaleDTOResponse = pagamentiPortaleDAO.leggiPagamentoPortale(leggiPagamentoPortaleDTO);

			Pagamento pagamento = PagamentiConverter.toRsModel(pagamentoPortaleDTOResponse);
			
			context.getLogger().info("Invocazione completata con successo");
			return pagamento;
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw WebApplicationExceptionMapper.handleException(e);
//			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
	}

	/**
	 * Dettaglio di un pagamento
	 *
	 */
	@Override
	public Pagamento getPagamentoByIdSession(String idSession) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			getAuthorizationRules().authorize(context);
			context.getLogger().debug("Autorizzazione completata con successo");     

			LeggiPagamentoPortaleDTO leggiPagamentoPortaleDTO = new LeggiPagamentoPortaleDTO(context.getAuthentication());
			leggiPagamentoPortaleDTO.setIdSessionePsp(idSession);
			leggiPagamentoPortaleDTO.setRisolviLink(true); 
			
			PagamentiPortaleDAO pagamentiPortaleDAO = new PagamentiPortaleDAO(); 
			
			LeggiPagamentoPortaleDTOResponse pagamentoPortaleDTOResponse = pagamentiPortaleDAO.leggiPagamentoPortale(leggiPagamentoPortaleDTO);

			Pagamento pagamento = PagamentiConverter.toRsModel(pagamentoPortaleDTOResponse);

			context.getLogger().info("Invocazione completata con successo");
			return pagamento;

		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw WebApplicationExceptionMapper.handleException(e);
//			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
	}

}

