package it.govpay.backoffice.v1.beans;

import java.net.URI;
import java.util.List;

import it.govpay.core.beans.Lista;

public class ListaStatisticheQuadrature extends Lista<StatisticaQuadratura> {
	
	public ListaStatisticheQuadrature(List<StatisticaQuadratura> pagamentiPortale, URI requestUri, long count, long pagina, long limit) {
		super(pagamentiPortale, requestUri, count, pagina, limit);
	}
	
}
