package it.govpay.model;

public class TipoVersamentoDominio extends TipoVersamento {
	private static final long serialVersionUID = 1L;
	
	private long idTipoVersamento;
	private long idDominio;
	private String codificaIuvCustom;
	private Tipo tipoCustom;
	private Boolean pagaTerziCustom;
	private Boolean abilitatoCustom;
	private String formTipoCustom;
	private String formDefinizioneCustom;
	private String validazioneDefinizioneCustom;
	private String trasformazioneTipoCustom;
	private String trasformazioneDefinizioneCustom;
	private String codApplicazioneCustom;
	private String promemoriaAvvisoTipoCustom;
	private Boolean promemoriaAvvisoPdfCustom;
	private String promemoriaAvvisoOggettoCustom;
	private String promemoriaAvvisoMessaggioCustom;
	private String promemoriaRicevutaTipoCustom;
	private Boolean promemoriaRicevutaPdfCustom;
	private String promemoriaRicevutaOggettoCustom;
	private String promemoriaRicevutaMessaggioCustom;
	private String visualizzazioneDefinizioneCustom;
	private String tracciatoCsvHeaderRispostaCustom;
	private String tracciatoCsvFreemarkerRichiestaCustom;
	private String tracciatoCsvFreemarkerRispostaCustom;
	
	public String getPromemoriaAvvisoTipoCustom() {
		return promemoriaAvvisoTipoCustom;
	}
	public void setPromemoriaAvvisoTipoCustom(String promemoriaAvvisoTipoCustom) {
		this.promemoriaAvvisoTipoCustom = promemoriaAvvisoTipoCustom;
	}
	public String getPromemoriaRicevutaTipoCustom() {
		return promemoriaRicevutaTipoCustom;
	}
	public void setPromemoriaRicevutaTipoCustom(String promemoriaRicevutaTipoCustom) {
		this.promemoriaRicevutaTipoCustom = promemoriaRicevutaTipoCustom;
	}
	
	public String getFormTipoCustom() {
		return formTipoCustom;
	}
	public void setFormTipoCustom(String formTipoCustom) {
		this.formTipoCustom = formTipoCustom;
	}
	public String getFormDefinizioneCustom() {
		return formDefinizioneCustom;
	}
	public void setFormDefinizioneCustom(String formDefinizioneCustom) {
		this.formDefinizioneCustom = formDefinizioneCustom;
	}
	public String getValidazioneDefinizioneCustom() {
		return validazioneDefinizioneCustom;
	}
	public void setValidazioneDefinizioneCustom(String validazioneDefinizioneCustom) {
		this.validazioneDefinizioneCustom = validazioneDefinizioneCustom;
	}
	public String getTrasformazioneTipoCustom() {
		return trasformazioneTipoCustom;
	}
	public void setTrasformazioneTipoCustom(String trasformazioneTipoCustom) {
		this.trasformazioneTipoCustom = trasformazioneTipoCustom;
	}
	public String getTrasformazioneDefinizioneCustom() {
		return trasformazioneDefinizioneCustom;
	}
	public void setTrasformazioneDefinizioneCustom(String trasformazioneDefinizioneCustom) {
		this.trasformazioneDefinizioneCustom = trasformazioneDefinizioneCustom;
	}
	public String getCodApplicazioneCustom() {
		return codApplicazioneCustom;
	}
	public void setCodApplicazioneCustom(String codApplicazioneCustom) {
		this.codApplicazioneCustom = codApplicazioneCustom;
	}
	public Boolean getPromemoriaAvvisoPdfCustom() {
		return promemoriaAvvisoPdfCustom;
	}
	public void setPromemoriaAvvisoPdfCustom(Boolean promemoriaAvvisoPdfCustom) {
		this.promemoriaAvvisoPdfCustom = promemoriaAvvisoPdfCustom;
	}
	public String getPromemoriaAvvisoOggettoCustom() {
		return promemoriaAvvisoOggettoCustom;
	}
	public void setPromemoriaAvvisoOggettoCustom(String promemoriaAvvisoOggettoCustom) {
		this.promemoriaAvvisoOggettoCustom = promemoriaAvvisoOggettoCustom;
	}
	public String getPromemoriaAvvisoMessaggioCustom() {
		return promemoriaAvvisoMessaggioCustom;
	}
	public void setPromemoriaAvvisoMessaggioCustom(String promemoriaAvvisoMessaggioCustom) {
		this.promemoriaAvvisoMessaggioCustom = promemoriaAvvisoMessaggioCustom;
	}
	public Boolean getPromemoriaRicevutaPdfCustom() {
		return promemoriaRicevutaPdfCustom;
	}
	public void setPromemoriaRicevutaPdfCustom(Boolean promemoriaRicevutaPdfCustom) {
		this.promemoriaRicevutaPdfCustom = promemoriaRicevutaPdfCustom;
	}
	public String getPromemoriaRicevutaOggettoCustom() {
		return promemoriaRicevutaOggettoCustom;
	}
	public void setPromemoriaRicevutaOggettoCustom(String promemoriaRicevutaOggettoCustom) {
		this.promemoriaRicevutaOggettoCustom = promemoriaRicevutaOggettoCustom;
	}
	public String getPromemoriaRicevutaMessaggioCustom() {
		return promemoriaRicevutaMessaggioCustom;
	}
	public void setPromemoriaRicevutaMessaggioCustom(String promemoriaRicevutaMessaggioCustom) {
		this.promemoriaRicevutaMessaggioCustom = promemoriaRicevutaMessaggioCustom;
	}
	public long getIdTipoVersamento() {
		return idTipoVersamento;
	}
	public void setIdTipoVersamento(long idTipoVersamento) {
		this.idTipoVersamento = idTipoVersamento;
	}
	public long getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(long idDominio) {
		this.idDominio = idDominio;
	}
	public String getCodificaIuvCustom() {
		return codificaIuvCustom;
	}
	public void setCodificaIuvCustom(String codificaIuvCustom) {
		this.codificaIuvCustom = codificaIuvCustom;
	}
	public Tipo getTipoCustom() {
		return tipoCustom;
	}
	public void setTipoCustom(Tipo tipoCustom) {
		this.tipoCustom = tipoCustom;
	}
	public Boolean getPagaTerziCustom() {
		return pagaTerziCustom;
	}
	public void setPagaTerziCustom(Boolean pagaTerziCustom) {
		this.pagaTerziCustom = pagaTerziCustom;
	}
	public Boolean getAbilitatoCustom() {
		return this.abilitatoCustom;
	}
	public void setAbilitatoCustom(Boolean abilitatoCustom) {
		this.abilitatoCustom = abilitatoCustom;
	}
	public String getVisualizzazioneDefinizioneCustom() {
		return visualizzazioneDefinizioneCustom;
	}
	public void setVisualizzazioneDefinizioneCustom(String visualizzazioneDefinizioneCustom) {
		this.visualizzazioneDefinizioneCustom = visualizzazioneDefinizioneCustom;
	}
	public String getTracciatoCsvHeaderRispostaCustom() {
		return tracciatoCsvHeaderRispostaCustom;
	}
	public void setTracciatoCsvHeaderRispostaCustom(String tracciatoCsvHeaderRispostaCustom) {
		this.tracciatoCsvHeaderRispostaCustom = tracciatoCsvHeaderRispostaCustom;
	}
	public String getTracciatoCsvFreemarkerRichiestaCustom() {
		return tracciatoCsvFreemarkerRichiestaCustom;
	}
	public void setTracciatoCsvFreemarkerRichiestaCustom(String tracciatoCsvFreemarkerRichiestaCustom) {
		this.tracciatoCsvFreemarkerRichiestaCustom = tracciatoCsvFreemarkerRichiestaCustom;
	}
	public String getTracciatoCsvFreemarkerRispostaCustom() {
		return tracciatoCsvFreemarkerRispostaCustom;
	}
	public void setTracciatoCsvFreemarkerRispostaCustom(String tracciatoCsvFreemarkerRispostaCustom) {
		this.tracciatoCsvFreemarkerRispostaCustom = tracciatoCsvFreemarkerRispostaCustom;
	}
}
