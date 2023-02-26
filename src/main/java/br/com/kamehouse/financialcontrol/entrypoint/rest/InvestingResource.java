package br.com.kamehouse.financialcontrol.entrypoint.rest;

import br.com.kamehouse.financialcontrol.core.usecase.InvestmentUsecase;
import br.com.kamehouse.financialcontrol.entrypoint.dto.InvestmentDto;
import br.com.kamehouse.financialcontrol.entrypoint.dto.QueryDto;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path(value = "/v1/apis/investings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvestingResource {

    @Inject
    InvestmentUsecase investmentUsecase;
    @Inject Validator validator;

    @GET
    public Response index(){
        var result = this.investmentUsecase.recoveryRecords();
        return Response.ok().entity(result).build();
    }

    @POST
    public Response create(InvestmentDto investmentDto){
        Set<ConstraintViolation<InvestmentDto>> violations = validator.validate(investmentDto);
        if(!violations.isEmpty()){
            Map<String, Object> errors = new HashMap<>();
            violations.stream().toList().forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));
            return Response.status(400).entity(errors).build();
        }
        var result = this.investmentUsecase.create(investmentDto);
        return Response.status(201).entity(result).build();
    }

    @PUT
    public Response update(InvestmentDto investmentDto){
        var result = this.investmentUsecase.update(investmentDto);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path(value = "/filter")
    public Response recoveryOf(List<QueryDto> queryDtoList){
        var result = this.investmentUsecase.recoveryRecords(queryDtoList);
        return Response.ok().entity(result).build();
    }

    @GET
    @Path(value = "/month/{mes}/year/{ano}")
    public Response recoveryOf(@PathParam("mes") Integer month, @PathParam("ano") Integer year){
        var result = this.investmentUsecase.recoveryRecordsOfMonthAndYear(month, year);
        return Response.ok().entity(result).build();
    }
}
