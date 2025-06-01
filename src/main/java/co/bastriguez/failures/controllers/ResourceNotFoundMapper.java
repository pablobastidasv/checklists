package co.bastriguez.failures.controllers;

import co.bastriguez.failures.models.ResourceNotFound;
import jakarta.json.Json;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFound> {
  @Override
  public Response toResponse(ResourceNotFound exception) {
    var payload = Json.createObjectBuilder()
      .add("type", "not-found")
      .add("title", "Resource Not Found")
      .add("detail", exception.getMessage())
      .add("timestamp", java.time.Instant.now().toString()) // TODO: Use a clock
      .build()
      .toString();

    return Response.status(Response.Status.NOT_FOUND)
      .entity(payload)
      .build();
  }
}
