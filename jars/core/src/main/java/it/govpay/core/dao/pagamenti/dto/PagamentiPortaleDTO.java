package it.govpay.core.dao.pagamenti.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.springframework.security.core.Authentication;

import it.govpay.core.dao.anagrafica.dto.BasicCreateRequestDTO;
import it.govpay.core.dao.commons.Anagrafica;
import it.govpay.model.Versamento.ModoAvvisatura;

public class PagamentiPortaleDTO  extends BasicCreateRequestDTO{

	public PagamentiPortaleDTO(Authentication user) {
		super(user);
	}

	private String idSessione = null;
	private String idSessionePortale =null;
	private String jsonRichiesta = null;
	private String urlRitorno = null;
	private String ibanAddebito =null;
	private String bicAddebito = null;
	private Date dataEsecuzionePagamento = null;
	private String credenzialiPagatore = null;
	private String lingua = null;
	private Anagrafica versante = null;
	private List<Object> pendenzeOrPendenzeRef = null;
	private String autenticazioneSoggetto = null;
	private Boolean avvisaturaDigitale;
	private ModoAvvisatura avvisaturaModalita;
	private MultivaluedMap<String, String> queryParameters;
	private MultivaluedMap<String, String> pathParameters;
	private Map<String, String> headers;

	public String getJsonRichiesta() {
		return this.jsonRichiesta;
	}
	public void setJsonRichiesta(String jsonRichiesta) {
		this.jsonRichiesta = jsonRichiesta;
	}
	public String getUrlRitorno() {
		return this.urlRitorno;
	}
	public void setUrlRitorno(String urlRitorno) {
		this.urlRitorno = urlRitorno;
	}
	public String getIbanAddebito() {
		return this.ibanAddebito;
	}
	public void setIbanAddebito(String ibanAddebito) {
		this.ibanAddebito = ibanAddebito;
	}
	public String getBicAddebito() {
		return this.bicAddebito;
	}
	public void setBicAddebito(String bicAddebito) {
		this.bicAddebito = bicAddebito;
	}
	public Date getDataEsecuzionePagamento() {
		return this.dataEsecuzionePagamento;
	}
	public void setDataEsecuzionePagamento(Date dataEsecuzionePagamento) {
		this.dataEsecuzionePagamento = dataEsecuzionePagamento;
	}
	public String getCredenzialiPagatore() {
		return this.credenzialiPagatore;
	}
	public void setCredenzialiPagatore(String credenzialiPagatore) {
		this.credenzialiPagatore = credenzialiPagatore;
	}
	public String getLingua() {
		return this.lingua;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public Anagrafica getVersante() {
		return this.versante;
	}
	public void setVersante(Anagrafica versante) {
		this.versante = versante;
	}
	public List<Object> getPendenzeOrPendenzeRef() {
		return this.pendenzeOrPendenzeRef;
	}
	public void setPendenzeOrPendenzeRef(List<Object> pendenzeOrPendenzeRef) {
		this.pendenzeOrPendenzeRef = pendenzeOrPendenzeRef;
	}
	public String getIdSessione() {
		return this.idSessione;
	}
	public void setIdSessione(String idSessione) {
		this.idSessione = idSessione;
	}
	public String getIdSessionePortale() {
		return this.idSessionePortale;
	}
	public void setIdSessionePortale(String idSessionePortale) {
		this.idSessionePortale = idSessionePortale;
	}
	public String getAutenticazioneSoggetto() {
		return this.autenticazioneSoggetto;
	}
	public void setAutenticazioneSoggetto(String autenticazioneSoggetto) {
		this.autenticazioneSoggetto = autenticazioneSoggetto;
	}
	public Boolean getAvvisaturaDigitale() {
		return this.avvisaturaDigitale;
	}
	public void setAvvisaturaDigitale(Boolean avvisaturaDigitale) {
		this.avvisaturaDigitale = avvisaturaDigitale;
	}
	public ModoAvvisatura getAvvisaturaModalita() {
		return avvisaturaModalita;
	}
	public void setAvvisaturaModalita(ModoAvvisatura avvisaturaModalita) {
		this.avvisaturaModalita = avvisaturaModalita;
	}
	public MultivaluedMap<String, String> getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(MultivaluedMap<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public MultivaluedMap<String, String> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(MultivaluedMap<String, String> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public class RefVersamentoAvviso {

		private String idDominio;
		private String numeroAvviso;
		private String idDebitore;
		
		public String getIdDominio() {
			return this.idDominio;
		}
		public void setIdDominio(String idDominio) {
			this.idDominio = idDominio;
		}
		public String getNumeroAvviso() {
			return this.numeroAvviso;
		}
		public void setNumeroAvviso(String numeroAvviso) {
			this.numeroAvviso = numeroAvviso;
		}
		public String getIdDebitore() {
			return idDebitore;
		}
		public void setIdDebitore(String idDebitore) {
			this.idDebitore = idDebitore;
		}
	}

	public class RefVersamentoPendenza {

		private String idA2A;
		private String idPendenza;
		public String getIdA2A() {
			return this.idA2A;
		}
		public void setIdA2A(String idA2A) {
			this.idA2A = idA2A;
		}
		public String getIdPendenza() {
			return this.idPendenza;
		}
		public void setIdPendenza(String idPendenza) {
			this.idPendenza = idPendenza;
		}
	}

	public class RefVersamentoModello4 {

		private String idDominio;
		private String idTipoPendenza;
		private String dati;
		public String getIdDominio() {
			return idDominio;
		}
		public void setIdDominio(String idDominio) {
			this.idDominio = idDominio;
		}
		public String getIdTipoPendenza() {
			return idTipoPendenza;
		}
		public void setIdTipoPendenza(String idTipoPendenza) {
			this.idTipoPendenza = idTipoPendenza;
		}
		public String getDati() {
			return dati;
		}
		public void setDati(String dati) {
			this.dati = dati;
		}

	}
}
