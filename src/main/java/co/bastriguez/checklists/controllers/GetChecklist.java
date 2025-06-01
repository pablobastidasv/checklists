package co.bastriguez.checklists.controllers;

import co.bastriguez.checklists.services.RetrieveChecklist;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.UUID;

@ApplicationScoped
@Path("checklists")
public class GetChecklist {

  @Inject
  RetrieveChecklist retrieveChecklist;

  public record ChecklistResponse(
    UUID id,
    String name,
    String description,
    UUID ownerId
  ) {
  }

  @GET
  @Path("/{id}")
  @Authenticated
  public ChecklistResponse getChecklist(@PathParam("id") UUID id) {
    var checklist = retrieveChecklist.retrieve(id);

    return new ChecklistResponse(
      checklist.id,
      checklist.name,
      checklist.description,
      checklist.owner.id
    );
  }

}
