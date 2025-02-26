package it.govpay.backoffice.v1;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.backoffice.v1.controllers.ReportisticheController;
import it.govpay.rs.v1.BaseRsServiceV1;


@Path("/reportistiche")

public class Reportistiche extends BaseRsServiceV1{


	private ReportisticheController controller = null;

	public Reportistiche() throws ServiceException {
		super("reportistiche");
		this.controller = new ReportisticheController(this.nomeServizio,this.log);
	}



    @GET
    @Path("/entrate-previste")
    
    @Produces({ "application/json", "application/pdf" })
    public Response getReportEntratePreviste(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @QueryParam("pagina") Integer pagina, @QueryParam("risultatiPerPagina") Integer risultatiPerPagina, @QueryParam("idDominio") String idDominio, @QueryParam("dataDa") String dataDa, @QueryParam("dataA") String dataA){
        this.controller.setContext(this.getContext());
        return this.controller.getReportEntratePreviste(this.getUser(), uriInfo, httpHeaders, pagina, risultatiPerPagina, idDominio, dataDa, dataA);
    }

}


