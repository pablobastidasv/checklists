package co.bastriguez.checklists.controllers;

import co.bastriguez.checklists.services.CreateChecklist;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@Path("checklists")
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class PostChecklist {

  @Inject
  CreateChecklist createChecklist;

  @Inject
  JsonWebToken identity;

  public record Payload(
    UUID id,
    @NotBlank
    String name,
    String description
  ) {
  }

  @POST
  @ResponseStatus(RestResponse.StatusCode.CREATED)
  public void postChecklist(@Valid Payload input) {
    var ownerId = UUID.fromString(identity.getSubject());
    createChecklist.create(input.id(), input.name(), input.description(), ownerId);
  }

}
