package br.com.kamehouse.financialcontrol.entrypoint.rest;

import br.com.kamehouse.financialcontrol.core.usecase.ExpenseUsecase;
import br.com.kamehouse.financialcontrol.entrypoint.dto.ExpenseDto;
import br.com.kamehouse.financialcontrol.entrypoint.dto.QueryDto;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path(value = "/v1/apis/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {

    @Inject ExpenseUsecase expenseUsecase;
    @Inject Validator validator;

    @GET
    public Response index(){
        var result = this.expenseUsecase.recoveryRecords();
        return Response.ok().entity(result).build();
    }

    @POST
    public Response create(ExpenseDto expenseDto){
        Set<ConstraintViolation<ExpenseDto>> violations = validator.validate(expenseDto);
        if(!violations.isEmpty()){
            Map<String, Object> errors = new HashMap<>();
            violations.stream().toList().forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));
            return Response.status(400).entity(errors).build();
        }
        var result = this.expenseUsecase.create(expenseDto);
        return Response.status(201).entity(result).build();
    }

    @PUT
    public Response update(ExpenseDto expenseDto){
        var result = this.expenseUsecase.update(expenseDto);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path(value = "/filter")
    public Response recoveryOf(List<QueryDto> queryDtoList){
        var result = this.expenseUsecase.recoveryRecords(queryDtoList);
        return Response.ok().entity(result).build();
    }
}
