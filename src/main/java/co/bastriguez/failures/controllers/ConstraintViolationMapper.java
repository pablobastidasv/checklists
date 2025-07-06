package co.bastriguez.failures.controllers;

import co.bastriguez.failures.models.Failure;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.Iterator;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

  private static final Logger logger = Logger.getLogger(ConstraintViolationMapper.class);

  private static String propertyName(Path path) {
    logger.debug("Path to constraint violation field " + path);

    Iterator<Path.Node> iterator = path.iterator();
    Path.Node node = null;
    while (iterator.hasNext()) {
      node = iterator.next();
    }
    return node == null ? "unknown" : node.getName();
  }


  @Override
  public Response toResponse(ConstraintViolationException exception) {
    var violations = exception.getConstraintViolations().stream()
      .peek(v -> logger.error("Constraint violation: " + v.getMessage()))
      .map(v -> new Failure.Violation(
        propertyName(v.getPropertyPath()),
        v.getInvalidValue() != null ? v.getInvalidValue().toString() : null,
        v.getMessage()
      )).toList();

    var failure = new Failure(
      "validation-error",
      "Validation Failed",
      "Information is missing or invalid",
      java.time.Instant.now().toString(), // TODO: Use a clock
      violations.toArray(new Failure.Violation[0])
    );

    return Response.status(422)
      .entity(failure)
      .build();
  }
}
