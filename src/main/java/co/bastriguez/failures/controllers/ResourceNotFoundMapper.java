package co.bastriguez.failures.controllers;

import co.bastriguez.failures.models.ResourceNotFound;
import jakarta.json.Json;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFound> {
  private static final Logger logger = Logger.getLogger(ResourceNotFoundMapper.class);

  @Override
  public Response toResponse(ResourceNotFound exception) {
    logger.debug("Mapping ResourceNotFound exception: " + exception.getMessage());

    var payload = Json.createObjectBuilder()
      .add("type", "not-found")
      .add("title", "Resource Not Found")
      .add("detail", exception.getMessage())
      .add("timestamp", java.time.Instant.now().toString()) // TODO: Use a clock
      .build()
      .toString();

    return Response.status(Response.Status.NOT_FOUND)
      .header("Content-Type", "application/problem+json")
      .entity(payload)
      .build();
  }
}
