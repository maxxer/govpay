package it.govpay.ragioneria.v2;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.ragioneria.v2.controller.ProfiloController;
import it.govpay.rs.v2.BaseRsServiceV2;


@Path("/profilo")

public class Profilo extends BaseRsServiceV2{


	private ProfiloController controller = null;

	public Profilo() throws ServiceException {
		super("profilo");
		this.controller = new ProfiloController(this.nomeServizio,this.log);
	}



    @GET
    @Path("/")
    
    @Produces({ "application/json" })
    public Response getProfilo(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders){
        this.controller.setContext(this.getContext());
        return this.controller.getProfilo(this.getUser(), uriInfo, httpHeaders);
    }

}


