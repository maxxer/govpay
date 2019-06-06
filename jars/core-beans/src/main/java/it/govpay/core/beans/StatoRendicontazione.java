
package it.govpay.core.beans;

public enum StatoRendicontazione {

    OK,
    ANOMALA,
    ALTRO_INTERMEDIARIO;

    public String value() {
        return this.name();
    }

    public static StatoRendicontazione fromValue(String v) {
        return valueOf(v);
    }

}
