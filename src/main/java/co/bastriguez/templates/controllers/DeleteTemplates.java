package co.bastriguez.templates.controllers;

import co.bastriguez.templates.services.DisabledTemplate;
import co.bastriguez.shared.infra.validators.Uuid;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@Path("/templates")
@Transactional
public class DeleteTemplates {

  @Inject
  DisabledTemplate disabledTemplate;

  @DELETE
  @Path("{id}")
  @ResponseStatus(RestResponse.StatusCode.NO_CONTENT)
  @Authenticated
  public void delete(@PathParam("id") @Uuid String id) {
    disabledTemplate.delete(UUID.fromString(id));
  }
}
