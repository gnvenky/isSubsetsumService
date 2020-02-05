package com.subsetsum.rest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.subsetsum.rest.dao.SubsetsumDB;
import com.subsetsum.rest.representations.Subsetsum;
import com.subsetsum.rest.solver.SubsetsumSolver;

@Path("/subsetsum")
@Produces(MediaType.APPLICATION_JSON)
public class SubsetsumRESTController {
  private final Validator validator;

  public SubsetsumRESTController(Validator validator) {
    this.validator = validator;
  }

  @GET
  public Response getSubsetsums() {
    return Response.ok(SubsetsumDB.getSubsetsums()).build();
  }

  @GET
  @Path("/{id}")
  public Response getsubsetsumById(@PathParam("id") Integer id) {
    Subsetsum subsetsum = SubsetsumDB.getSubsetsum(id);
    if (subsetsum != null) {
      Response.ResponseBuilder rb = Response.ok(subsetsum);
      SubsetsumSolver sl = new SubsetsumSolver();
      String str = subsetsum.getArray();

      List list = new ArrayList<Integer>();
      for (String s : str.substring(1, str.length() - 1).split(",")) {
        String trim = s.trim();
        int i = Integer.parseInt(trim);
        list.add(i);
      }
      boolean isFeasible = sl.isFeasible(list, subsetsum.getSum());
      rb.header("x-Feasible", isFeasible);
      return rb.build();
    }
    else
      return Response.status(Status.NOT_FOUND).build();
  }

  @POST
  public Response createSubsetsum(Subsetsum subsetsum) throws URISyntaxException {
    // validation
    Set<ConstraintViolation<Subsetsum>> violations = validator.validate(subsetsum);
    Subsetsum e = SubsetsumDB.getSubsetsum(subsetsum.getId());
    if (violations.size() > 0) {
      ArrayList<String> validationMessages = new ArrayList<String>();
      for (ConstraintViolation<Subsetsum> violation : violations) {
        validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
      }
      return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
    }
    if (e != null) {
      SubsetsumDB.updateSubsetsum(subsetsum.getId(), subsetsum);
      return Response.created(new URI("/subsetsum/" + subsetsum.getId()))
        .build();
    } else
      return Response.status(Status.NOT_FOUND).build();
  }

  @PUT
  @Path("/{id}")
  public Response updateSubsetsumById(@PathParam("id") Integer id, Subsetsum subsetsum) {
    // validation
    Set<ConstraintViolation<Subsetsum>> violations = validator.validate(subsetsum);
    Subsetsum e = SubsetsumDB.getSubsetsum(subsetsum.getId());
    if (violations.size() > 0) {
      ArrayList<String> validationMessages = new ArrayList<String>();
      for (ConstraintViolation<Subsetsum> violation : violations) {
        validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
      }
      return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
    }
    if (e != null) {
      subsetsum.setId(id);
      SubsetsumDB.updateSubsetsum(id, subsetsum);
      return Response.ok(subsetsum).build();
    } else
      return Response.status(Status.NOT_FOUND).build();
  }

  @DELETE
  @Path("/{id}")
  public Response removeSubsetsumById(@PathParam("id") Integer id) {
    Subsetsum subsetsum = SubsetsumDB.getSubsetsum(id);
    if (subsetsum != null) {
      SubsetsumDB.removeSubsetsum(id);
      return Response.ok().build();
    } else
      return Response.status(Status.NOT_FOUND).build();
  }
}


