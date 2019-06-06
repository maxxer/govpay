package it.govpay.core.business.model;

public class Iuv {

	private String codApplicazione;
	private String codVersamentoEnte;
	private String codDominio;
	private String iuv;
	private String numeroAvviso;
	private byte[] qrCode;
	private byte[] barCode;

	public String getCodApplicazione() {
		return this.codApplicazione;
	}

	public void setCodApplicazione(String value) {
		this.codApplicazione = value;
	}

	public String getCodVersamentoEnte() {
		return this.codVersamentoEnte;
	}

	public void setCodVersamentoEnte(String value) {
		this.codVersamentoEnte = value;
	}

	public String getCodDominio() {
		return this.codDominio;
	}

	public void setCodDominio(String value) {
		this.codDominio = value;
	}

	public String getIuv() {
		return this.iuv;
	}

	public void setIuv(String value) {
		this.iuv = value;
	}

	public String getNumeroAvviso() {
		return this.numeroAvviso;
	}

	public void setNumeroAvviso(String value) {
		this.numeroAvviso = value;
	}

	public byte[] getQrCode() {
		return this.qrCode;
	}

	public void setQrCode(byte[] value) {
		this.qrCode = value;
	}

	public byte[] getBarCode() {
		return this.barCode;
	}

	public void setBarCode(byte[] value) {
		this.barCode = value;
	}
}
