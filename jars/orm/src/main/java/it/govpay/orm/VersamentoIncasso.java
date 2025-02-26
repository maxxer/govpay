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
package it.govpay.orm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for VersamentoIncasso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VersamentoIncasso">
 * 		&lt;sequence>
 * 			&lt;element name="codVersamentoEnte" type="{http://www.govpay.it/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nome" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idTipoVersamentoDominio" type="{http://www.govpay.it/orm}id-tipo-versamento-dominio" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idTipoVersamento" type="{http://www.govpay.it/orm}id-tipo-versamento" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idDominio" type="{http://www.govpay.it/orm}id-dominio" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idUo" type="{http://www.govpay.it/orm}id-uo" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idApplicazione" type="{http://www.govpay.it/orm}id-applicazione" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idPagamentoPortale" type="{http://www.govpay.it/orm}id-pagamento-portale" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="iuv" type="{http://www.govpay.it/orm}iuv-search" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="importoTotale" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoVersamento" type="{http://www.govpay.it/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizioneStato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="aggiornabile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataCreazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataOraUltimoAggiornamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="causaleVersamento" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreTipo" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreIdentificativo" type="{http://www.govpay.it/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="debitoreAnagrafica" type="{http://www.govpay.it/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="debitoreIndirizzo" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreCivico" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreCap" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreLocalita" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreProvincia" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreNazione" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreEmail" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreTelefono" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreCellulare" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="debitoreFax" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tassonomiaAvviso" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tassonomia" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codLotto" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codVersamentoLotto" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codAnnoTributario" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codBundlekey" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="datiAllegati" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="incasso" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="anomalie" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="iuvVersamento" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="numeroAvviso" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="avvisaturaAbilitata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="avvisaturaDaInviare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="avvisaturaOperazione" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="avvisaturaModalita" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="avvisaturaTipoPagamento" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="avvisaturaCodAvvisatura" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idTracciatoAvvisatura" type="{http://www.govpay.it/orm}id-tracciato" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="ack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="anomalo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="importoPagato" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="importoIncassato" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="statoPagamento" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="iuvPagamento" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="divisione" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="direzione" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="smartOrderDate" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="smartOrderRank" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Giovanni Bussu (bussu@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VersamentoIncasso", 
  propOrder = {
  	"codVersamentoEnte",
  	"nome",
  	"idTipoVersamentoDominio",
  	"idTipoVersamento",
  	"idDominio",
  	"idUo",
  	"idApplicazione",
  	"idPagamentoPortale",
  	"iuv",
  	"importoTotale",
  	"statoVersamento",
  	"descrizioneStato",
  	"aggiornabile",
  	"dataCreazione",
  	"dataValidita",
  	"dataScadenza",
  	"dataOraUltimoAggiornamento",
  	"causaleVersamento",
  	"debitoreTipo",
  	"debitoreIdentificativo",
  	"debitoreAnagrafica",
  	"debitoreIndirizzo",
  	"debitoreCivico",
  	"debitoreCap",
  	"debitoreLocalita",
  	"debitoreProvincia",
  	"debitoreNazione",
  	"debitoreEmail",
  	"debitoreTelefono",
  	"debitoreCellulare",
  	"debitoreFax",
  	"tassonomiaAvviso",
  	"tassonomia",
  	"codLotto",
  	"codVersamentoLotto",
  	"codAnnoTributario",
  	"codBundlekey",
  	"datiAllegati",
  	"incasso",
  	"anomalie",
  	"iuvVersamento",
  	"numeroAvviso",
  	"avvisaturaAbilitata",
  	"avvisaturaDaInviare",
  	"avvisaturaOperazione",
  	"avvisaturaModalita",
  	"avvisaturaTipoPagamento",
  	"avvisaturaCodAvvisatura",
  	"idTracciatoAvvisatura",
  	"ack",
  	"anomalo",
  	"dataPagamento",
  	"importoPagato",
  	"importoIncassato",
  	"statoPagamento",
  	"iuvPagamento",
  	"divisione",
  	"direzione",
  	"smartOrderDate",
  	"smartOrderRank"
  }
)

@XmlRootElement(name = "VersamentoIncasso")

public class VersamentoIncasso extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public VersamentoIncasso() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return new Long(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=new Long(-1);
  }

  public java.lang.String getCodVersamentoEnte() {
    return this.codVersamentoEnte;
  }

  public void setCodVersamentoEnte(java.lang.String codVersamentoEnte) {
    this.codVersamentoEnte = codVersamentoEnte;
  }

  public java.lang.String getNome() {
    return this.nome;
  }

  public void setNome(java.lang.String nome) {
    this.nome = nome;
  }

  public IdTipoVersamentoDominio getIdTipoVersamentoDominio() {
    return this.idTipoVersamentoDominio;
  }

  public void setIdTipoVersamentoDominio(IdTipoVersamentoDominio idTipoVersamentoDominio) {
    this.idTipoVersamentoDominio = idTipoVersamentoDominio;
  }

  public IdTipoVersamento getIdTipoVersamento() {
    return this.idTipoVersamento;
  }

  public void setIdTipoVersamento(IdTipoVersamento idTipoVersamento) {
    this.idTipoVersamento = idTipoVersamento;
  }

  public IdDominio getIdDominio() {
    return this.idDominio;
  }

  public void setIdDominio(IdDominio idDominio) {
    this.idDominio = idDominio;
  }

  public IdUo getIdUo() {
    return this.idUo;
  }

  public void setIdUo(IdUo idUo) {
    this.idUo = idUo;
  }

  public IdApplicazione getIdApplicazione() {
    return this.idApplicazione;
  }

  public void setIdApplicazione(IdApplicazione idApplicazione) {
    this.idApplicazione = idApplicazione;
  }

  public IdPagamentoPortale getIdPagamentoPortale() {
    return this.idPagamentoPortale;
  }

  public void setIdPagamentoPortale(IdPagamentoPortale idPagamentoPortale) {
    this.idPagamentoPortale = idPagamentoPortale;
  }

  public IuvSearch getIuv() {
    return this.iuv;
  }

  public void setIuv(IuvSearch iuv) {
    this.iuv = iuv;
  }

  public double getImportoTotale() {
    return this.importoTotale;
  }

  public void setImportoTotale(double importoTotale) {
    this.importoTotale = importoTotale;
  }

  public java.lang.String getStatoVersamento() {
    return this.statoVersamento;
  }

  public void setStatoVersamento(java.lang.String statoVersamento) {
    this.statoVersamento = statoVersamento;
  }

  public java.lang.String getDescrizioneStato() {
    return this.descrizioneStato;
  }

  public void setDescrizioneStato(java.lang.String descrizioneStato) {
    this.descrizioneStato = descrizioneStato;
  }

  public java.lang.String getAggiornabile() {
    return this.aggiornabile;
  }

  public void setAggiornabile(java.lang.String aggiornabile) {
    this.aggiornabile = aggiornabile;
  }

  public java.util.Date getDataCreazione() {
    return this.dataCreazione;
  }

  public void setDataCreazione(java.util.Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public java.util.Date getDataValidita() {
    return this.dataValidita;
  }

  public void setDataValidita(java.util.Date dataValidita) {
    this.dataValidita = dataValidita;
  }

  public java.util.Date getDataScadenza() {
    return this.dataScadenza;
  }

  public void setDataScadenza(java.util.Date dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public java.util.Date getDataOraUltimoAggiornamento() {
    return this.dataOraUltimoAggiornamento;
  }

  public void setDataOraUltimoAggiornamento(java.util.Date dataOraUltimoAggiornamento) {
    this.dataOraUltimoAggiornamento = dataOraUltimoAggiornamento;
  }

  public java.lang.String getCausaleVersamento() {
    return this.causaleVersamento;
  }

  public void setCausaleVersamento(java.lang.String causaleVersamento) {
    this.causaleVersamento = causaleVersamento;
  }

  public java.lang.String getDebitoreTipo() {
    return this.debitoreTipo;
  }

  public void setDebitoreTipo(java.lang.String debitoreTipo) {
    this.debitoreTipo = debitoreTipo;
  }

  public java.lang.String getDebitoreIdentificativo() {
    return this.debitoreIdentificativo;
  }

  public void setDebitoreIdentificativo(java.lang.String debitoreIdentificativo) {
    this.debitoreIdentificativo = debitoreIdentificativo;
  }

  public java.lang.String getDebitoreAnagrafica() {
    return this.debitoreAnagrafica;
  }

  public void setDebitoreAnagrafica(java.lang.String debitoreAnagrafica) {
    this.debitoreAnagrafica = debitoreAnagrafica;
  }

  public java.lang.String getDebitoreIndirizzo() {
    return this.debitoreIndirizzo;
  }

  public void setDebitoreIndirizzo(java.lang.String debitoreIndirizzo) {
    this.debitoreIndirizzo = debitoreIndirizzo;
  }

  public java.lang.String getDebitoreCivico() {
    return this.debitoreCivico;
  }

  public void setDebitoreCivico(java.lang.String debitoreCivico) {
    this.debitoreCivico = debitoreCivico;
  }

  public java.lang.String getDebitoreCap() {
    return this.debitoreCap;
  }

  public void setDebitoreCap(java.lang.String debitoreCap) {
    this.debitoreCap = debitoreCap;
  }

  public java.lang.String getDebitoreLocalita() {
    return this.debitoreLocalita;
  }

  public void setDebitoreLocalita(java.lang.String debitoreLocalita) {
    this.debitoreLocalita = debitoreLocalita;
  }

  public java.lang.String getDebitoreProvincia() {
    return this.debitoreProvincia;
  }

  public void setDebitoreProvincia(java.lang.String debitoreProvincia) {
    this.debitoreProvincia = debitoreProvincia;
  }

  public java.lang.String getDebitoreNazione() {
    return this.debitoreNazione;
  }

  public void setDebitoreNazione(java.lang.String debitoreNazione) {
    this.debitoreNazione = debitoreNazione;
  }

  public java.lang.String getDebitoreEmail() {
    return this.debitoreEmail;
  }

  public void setDebitoreEmail(java.lang.String debitoreEmail) {
    this.debitoreEmail = debitoreEmail;
  }

  public java.lang.String getDebitoreTelefono() {
    return this.debitoreTelefono;
  }

  public void setDebitoreTelefono(java.lang.String debitoreTelefono) {
    this.debitoreTelefono = debitoreTelefono;
  }

  public java.lang.String getDebitoreCellulare() {
    return this.debitoreCellulare;
  }

  public void setDebitoreCellulare(java.lang.String debitoreCellulare) {
    this.debitoreCellulare = debitoreCellulare;
  }

  public java.lang.String getDebitoreFax() {
    return this.debitoreFax;
  }

  public void setDebitoreFax(java.lang.String debitoreFax) {
    this.debitoreFax = debitoreFax;
  }

  public java.lang.String getTassonomiaAvviso() {
    return this.tassonomiaAvviso;
  }

  public void setTassonomiaAvviso(java.lang.String tassonomiaAvviso) {
    this.tassonomiaAvviso = tassonomiaAvviso;
  }

  public java.lang.String getTassonomia() {
    return this.tassonomia;
  }

  public void setTassonomia(java.lang.String tassonomia) {
    this.tassonomia = tassonomia;
  }

  public java.lang.String getCodLotto() {
    return this.codLotto;
  }

  public void setCodLotto(java.lang.String codLotto) {
    this.codLotto = codLotto;
  }

  public java.lang.String getCodVersamentoLotto() {
    return this.codVersamentoLotto;
  }

  public void setCodVersamentoLotto(java.lang.String codVersamentoLotto) {
    this.codVersamentoLotto = codVersamentoLotto;
  }

  public java.lang.String getCodAnnoTributario() {
    return this.codAnnoTributario;
  }

  public void setCodAnnoTributario(java.lang.String codAnnoTributario) {
    this.codAnnoTributario = codAnnoTributario;
  }

  public java.lang.String getCodBundlekey() {
    return this.codBundlekey;
  }

  public void setCodBundlekey(java.lang.String codBundlekey) {
    this.codBundlekey = codBundlekey;
  }

  public java.lang.String getDatiAllegati() {
    return this.datiAllegati;
  }

  public void setDatiAllegati(java.lang.String datiAllegati) {
    this.datiAllegati = datiAllegati;
  }

  public java.lang.String getIncasso() {
    return this.incasso;
  }

  public void setIncasso(java.lang.String incasso) {
    this.incasso = incasso;
  }

  public java.lang.String getAnomalie() {
    return this.anomalie;
  }

  public void setAnomalie(java.lang.String anomalie) {
    this.anomalie = anomalie;
  }

  public java.lang.String getIuvVersamento() {
    return this.iuvVersamento;
  }

  public void setIuvVersamento(java.lang.String iuvVersamento) {
    this.iuvVersamento = iuvVersamento;
  }

  public java.lang.String getNumeroAvviso() {
    return this.numeroAvviso;
  }

  public void setNumeroAvviso(java.lang.String numeroAvviso) {
    this.numeroAvviso = numeroAvviso;
  }

  public java.lang.String getAvvisaturaAbilitata() {
    return this.avvisaturaAbilitata;
  }

  public void setAvvisaturaAbilitata(java.lang.String avvisaturaAbilitata) {
    this.avvisaturaAbilitata = avvisaturaAbilitata;
  }

  public java.lang.String getAvvisaturaDaInviare() {
    return this.avvisaturaDaInviare;
  }

  public void setAvvisaturaDaInviare(java.lang.String avvisaturaDaInviare) {
    this.avvisaturaDaInviare = avvisaturaDaInviare;
  }

  public java.lang.String getAvvisaturaOperazione() {
    return this.avvisaturaOperazione;
  }

  public void setAvvisaturaOperazione(java.lang.String avvisaturaOperazione) {
    this.avvisaturaOperazione = avvisaturaOperazione;
  }

  public java.lang.String getAvvisaturaModalita() {
    return this.avvisaturaModalita;
  }

  public void setAvvisaturaModalita(java.lang.String avvisaturaModalita) {
    this.avvisaturaModalita = avvisaturaModalita;
  }

  public java.lang.Integer getAvvisaturaTipoPagamento() {
    return this.avvisaturaTipoPagamento;
  }

  public void setAvvisaturaTipoPagamento(java.lang.Integer avvisaturaTipoPagamento) {
    this.avvisaturaTipoPagamento = avvisaturaTipoPagamento;
  }

  public java.lang.String getAvvisaturaCodAvvisatura() {
    return this.avvisaturaCodAvvisatura;
  }

  public void setAvvisaturaCodAvvisatura(java.lang.String avvisaturaCodAvvisatura) {
    this.avvisaturaCodAvvisatura = avvisaturaCodAvvisatura;
  }

  public IdTracciato getIdTracciatoAvvisatura() {
    return this.idTracciatoAvvisatura;
  }

  public void setIdTracciatoAvvisatura(IdTracciato idTracciatoAvvisatura) {
    this.idTracciatoAvvisatura = idTracciatoAvvisatura;
  }

  public java.lang.String getAck() {
    return this.ack;
  }

  public void setAck(java.lang.String ack) {
    this.ack = ack;
  }

  public java.lang.String getAnomalo() {
    return this.anomalo;
  }

  public void setAnomalo(java.lang.String anomalo) {
    this.anomalo = anomalo;
  }

  public java.util.Date getDataPagamento() {
    return this.dataPagamento;
  }

  public void setDataPagamento(java.util.Date dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public java.lang.Double getImportoPagato() {
    return this.importoPagato;
  }

  public void setImportoPagato(java.lang.Double importoPagato) {
    this.importoPagato = importoPagato;
  }

  public java.lang.Double getImportoIncassato() {
    return this.importoIncassato;
  }

  public void setImportoIncassato(java.lang.Double importoIncassato) {
    this.importoIncassato = importoIncassato;
  }

  public java.lang.String getStatoPagamento() {
    return this.statoPagamento;
  }

  public void setStatoPagamento(java.lang.String statoPagamento) {
    this.statoPagamento = statoPagamento;
  }

  public java.lang.String getIuvPagamento() {
    return this.iuvPagamento;
  }

  public void setIuvPagamento(java.lang.String iuvPagamento) {
    this.iuvPagamento = iuvPagamento;
  }

  public java.lang.String getDivisione() {
    return this.divisione;
  }

  public void setDivisione(java.lang.String divisione) {
    this.divisione = divisione;
  }

  public java.lang.String getDirezione() {
    return this.direzione;
  }

  public void setDirezione(java.lang.String direzione) {
    this.direzione = direzione;
  }

  public long getSmartOrderDate() {
    return this.smartOrderDate;
  }

  public void setSmartOrderDate(long smartOrderDate) {
    this.smartOrderDate = smartOrderDate;
  }

  public java.lang.Integer getSmartOrderRank() {
    return this.smartOrderRank;
  }

  public void setSmartOrderRank(java.lang.Integer smartOrderRank) {
    this.smartOrderRank = smartOrderRank;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static it.govpay.orm.model.VersamentoIncassoModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(it.govpay.orm.VersamentoIncasso.modelStaticInstance==null){
  			it.govpay.orm.VersamentoIncasso.modelStaticInstance = new it.govpay.orm.model.VersamentoIncassoModel();
	  }
  }
  public static it.govpay.orm.model.VersamentoIncassoModel model(){
	  if(it.govpay.orm.VersamentoIncasso.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return it.govpay.orm.VersamentoIncasso.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codVersamentoEnte",required=true,nillable=false)
  protected java.lang.String codVersamentoEnte;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nome",required=false,nillable=false)
  protected java.lang.String nome;

  @XmlElement(name="idTipoVersamentoDominio",required=true,nillable=false)
  protected IdTipoVersamentoDominio idTipoVersamentoDominio;

  @XmlElement(name="idTipoVersamento",required=true,nillable=false)
  protected IdTipoVersamento idTipoVersamento;

  @XmlElement(name="idDominio",required=true,nillable=false)
  protected IdDominio idDominio;

  @XmlElement(name="idUo",required=false,nillable=false)
  protected IdUo idUo;

  @XmlElement(name="idApplicazione",required=true,nillable=false)
  protected IdApplicazione idApplicazione;

  @XmlElement(name="idPagamentoPortale",required=true,nillable=false)
  protected IdPagamentoPortale idPagamentoPortale;

  @XmlElement(name="iuv",required=false,nillable=false)
  protected IuvSearch iuv;

  @javax.xml.bind.annotation.XmlSchemaType(name="double")
  @XmlElement(name="importoTotale",required=true,nillable=false)
  protected double importoTotale;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="statoVersamento",required=true,nillable=false)
  protected java.lang.String statoVersamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizioneStato",required=false,nillable=false)
  protected java.lang.String descrizioneStato;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="aggiornabile",required=true,nillable=false)
  protected java.lang.String aggiornabile;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataCreazione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataCreazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataValidita",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataValidita;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataScadenza",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataScadenza;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataOraUltimoAggiornamento",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataOraUltimoAggiornamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="causaleVersamento",required=false,nillable=false)
  protected java.lang.String causaleVersamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreTipo",required=false,nillable=false)
  protected java.lang.String debitoreTipo;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreIdentificativo",required=true,nillable=false)
  protected java.lang.String debitoreIdentificativo;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreAnagrafica",required=true,nillable=false)
  protected java.lang.String debitoreAnagrafica;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreIndirizzo",required=false,nillable=false)
  protected java.lang.String debitoreIndirizzo;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreCivico",required=false,nillable=false)
  protected java.lang.String debitoreCivico;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreCap",required=false,nillable=false)
  protected java.lang.String debitoreCap;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreLocalita",required=false,nillable=false)
  protected java.lang.String debitoreLocalita;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreProvincia",required=false,nillable=false)
  protected java.lang.String debitoreProvincia;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreNazione",required=false,nillable=false)
  protected java.lang.String debitoreNazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreEmail",required=false,nillable=false)
  protected java.lang.String debitoreEmail;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreTelefono",required=false,nillable=false)
  protected java.lang.String debitoreTelefono;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreCellulare",required=false,nillable=false)
  protected java.lang.String debitoreCellulare;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="debitoreFax",required=false,nillable=false)
  protected java.lang.String debitoreFax;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="tassonomiaAvviso",required=false,nillable=false)
  protected java.lang.String tassonomiaAvviso;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="tassonomia",required=false,nillable=false)
  protected java.lang.String tassonomia;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codLotto",required=false,nillable=false)
  protected java.lang.String codLotto;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codVersamentoLotto",required=false,nillable=false)
  protected java.lang.String codVersamentoLotto;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codAnnoTributario",required=false,nillable=false)
  protected java.lang.String codAnnoTributario;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codBundlekey",required=false,nillable=false)
  protected java.lang.String codBundlekey;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="datiAllegati",required=false,nillable=false)
  protected java.lang.String datiAllegati;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="incasso",required=false,nillable=false)
  protected java.lang.String incasso;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="anomalie",required=false,nillable=false)
  protected java.lang.String anomalie;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="iuvVersamento",required=false,nillable=false)
  protected java.lang.String iuvVersamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="numeroAvviso",required=false,nillable=false)
  protected java.lang.String numeroAvviso;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="avvisaturaAbilitata",required=true,nillable=false)
  protected java.lang.String avvisaturaAbilitata;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="avvisaturaDaInviare",required=true,nillable=false)
  protected java.lang.String avvisaturaDaInviare;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="avvisaturaOperazione",required=false,nillable=false)
  protected java.lang.String avvisaturaOperazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="avvisaturaModalita",required=false,nillable=false)
  protected java.lang.String avvisaturaModalita;

  @javax.xml.bind.annotation.XmlSchemaType(name="positiveInteger")
  @XmlElement(name="avvisaturaTipoPagamento",required=false,nillable=false)
  protected java.lang.Integer avvisaturaTipoPagamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="avvisaturaCodAvvisatura",required=false,nillable=false)
  protected java.lang.String avvisaturaCodAvvisatura;

  @XmlElement(name="idTracciatoAvvisatura",required=false,nillable=false)
  protected IdTracciato idTracciatoAvvisatura;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="ack",required=true,nillable=false)
  protected java.lang.String ack;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="anomalo",required=true,nillable=false)
  protected java.lang.String anomalo;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataPagamento",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataPagamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="importoPagato",required=false,nillable=false)
  protected java.lang.Double importoPagato;

  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="importoIncassato",required=false,nillable=false)
  protected java.lang.Double importoIncassato;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="statoPagamento",required=false,nillable=false)
  protected java.lang.String statoPagamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="iuvPagamento",required=false,nillable=false)
  protected java.lang.String iuvPagamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="divisione",required=false,nillable=false)
  protected java.lang.String divisione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="direzione",required=false,nillable=false)
  protected java.lang.String direzione;

  @javax.xml.bind.annotation.XmlSchemaType(name="long")
  @XmlElement(name="smartOrderDate",required=false,nillable=false)
  protected long smartOrderDate;

  @javax.xml.bind.annotation.XmlSchemaType(name="positiveInteger")
  @XmlElement(name="smartOrderRank",required=false,nillable=false)
  protected java.lang.Integer smartOrderRank;

}
