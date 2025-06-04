package co.bastriguez.checklists.controllers;

import co.bastriguez.checklists.services.ListChecklists;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/checklists")
@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
public class GetChecklists {

  @Inject
  ListChecklists checklistsFinder;

  @GET
  @Authenticated
  public JsonObject getChecklists() {
    var data = checklistsFinder.getChecklists();

    var checklists = Json.createArrayBuilder();
    data.items().forEach(checklist -> {
      var i = Json.createObjectBuilder()
        .add("id", checklist.id().toString())
        .add("name", checklist.name())
        .add("description", checklist.description())
        .add("itemsCount", checklist.itemsCount())
        .build();

      checklists.add(i);
    });

    return Json.createObjectBuilder()
      .add("items", checklists)
      .build();
  }
}
