package co.bastriguez.templates.controllers;

import co.bastriguez.templates.services.RetrieveTemplate;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.UUID;

@Path("templates")
public class GetTemplate {

  @Inject
  RetrieveTemplate retrieveTemplate;

  public record TemplateResponse(
    UUID id,
    String name,
    String description,
    UUID ownerId
  ) {
  }

  @GET
  @Path("/{id}")
  @Authenticated
  public TemplateResponse getTemplate(@PathParam("id") UUID id) {
    var template = retrieveTemplate.retrieve(id);

    return new TemplateResponse(
      template.id,
      template.name,
      template.description,
      template.owner.id
    );
  }

}
