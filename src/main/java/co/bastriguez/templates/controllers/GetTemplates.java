package co.bastriguez.templates.controllers;

import co.bastriguez.templates.services.ListTemplates;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/templates")
@Consumes("application/json")
@Produces("application/json")
public class GetTemplates {

  @Inject
  ListTemplates templatesFinder;

  @GET
  @Authenticated
  public JsonObject getTemplates() {
    var data = templatesFinder.getTemplates();

    var templates = Json.createArrayBuilder();
    data.items().forEach(template -> {
      var i = Json.createObjectBuilder()
        .add("id", template.id().toString())
        .add("name", template.name())
        .add("description", template.description())
        .add("itemsCount", template.itemsCount())
        .build();

      templates.add(i);
    });

    return Json.createObjectBuilder()
      .add("items", templates)
      .build();
  }
}
