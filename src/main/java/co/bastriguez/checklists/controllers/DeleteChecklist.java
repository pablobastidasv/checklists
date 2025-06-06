package co.bastriguez.checklists.controllers;

import co.bastriguez.checklists.services.DisabledChecklist;
import co.bastriguez.shared.validators.Uuid;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@ApplicationScoped
@Path("/checklists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class DeleteChecklist {

  @Inject
  DisabledChecklist disabledChecklist;

  @DELETE
  @Path("{id}")
  @ResponseStatus(RestResponse.StatusCode.NO_CONTENT)
  @Authenticated
  public void delete(@PathParam("id") @Uuid String id) {
    disabledChecklist.delete(UUID.fromString(id));
  }
}
