package it.govpay.backoffice.v1;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.backoffice.v1.controllers.IncassiController;
import it.govpay.core.beans.Costanti;
import it.govpay.rs.v1.BaseRsServiceV1;


@Path("/incassi")

public class Incassi extends BaseRsServiceV1{


	private IncassiController controller = null;

	public Incassi() throws ServiceException {
		super("incassi");
		this.controller = new IncassiController(this.nomeServizio,this.log);
	}



    @GET
    @Path("/")
    
    @Produces({ "application/json" })
    public Response findRiconciliazioni(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @QueryParam(value=Costanti.PARAMETRO_PAGINA) @DefaultValue(value="1") Integer pagina, @QueryParam(value=Costanti.PARAMETRO_RISULTATI_PER_PAGINA) @DefaultValue(value="25") Integer risultatiPerPagina, @QueryParam("ordinamento") String ordinamento, @QueryParam("dataDa") String dataDa, @QueryParam("dataA") String dataA, @QueryParam("idDominio") String idDominio){
        this.controller.setContext(this.getContext());
        return this.controller.findRiconciliazioni(this.getUser(), uriInfo, httpHeaders, pagina, risultatiPerPagina, ordinamento, dataDa, dataA, idDominio);
    }

    @GET
    @Path("/{idDominio}/{idIncasso}")
    
    @Produces({ "application/json" })
    public Response getRiconciliazione(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @PathParam("idDominio") String idDominio, @PathParam("idIncasso") String idIncasso){
        this.controller.setContext(this.getContext());
        return this.controller.getRiconciliazione(this.getUser(), uriInfo, httpHeaders,  idDominio,  idIncasso);
    }

    @POST
    @Path("/{idDominio}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response addRiconciliazione(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @PathParam("idDominio") String idDominio, java.io.InputStream is){
        this.controller.setContext(this.getContext());
        return this.controller.addRiconciliazione(this.getUser(), uriInfo, httpHeaders,  idDominio, is);
    }

}


