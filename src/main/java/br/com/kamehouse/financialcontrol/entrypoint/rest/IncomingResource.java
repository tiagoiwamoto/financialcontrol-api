package br.com.kamehouse.financialcontrol.entrypoint.rest;

import br.com.kamehouse.financialcontrol.core.usecase.IncomingUsecase;
import br.com.kamehouse.financialcontrol.entrypoint.dto.IncomingDto;
import br.com.kamehouse.financialcontrol.entrypoint.dto.QueryDto;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(value = "/v1/apis/incomings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IncomingResource {

    @Inject IncomingUsecase incomingUsecase;

    @GET
    public Response index(){
        var result = this.incomingUsecase.recoveryRecords();
        return Response.ok().entity(result).build();
    }

    @POST
    public Response create(IncomingDto incomingDto){
        var result = this.incomingUsecase.create(incomingDto);
        return Response.status(201).entity(result).build();
    }

    @PUT
    public Response update(IncomingDto incomingDto){
        var result = this.incomingUsecase.update(incomingDto);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path(value = "/filter")
    public Response recoveryOf(List<QueryDto> queryDtoList){
        var result = this.incomingUsecase.recoveryRecords(queryDtoList);
        return Response.ok().entity(result).build();
    }
}
