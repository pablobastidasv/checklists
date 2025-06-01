package co.bastriguez.failures.controllers;

import co.bastriguez.failures.models.Failure;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.StreamSupport;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

  private static String propertyName(Path path) {
    return StreamSupport.stream(path.spliterator(), false)
      .skip(2)
      .map(Path.Node::getName)
      .findFirst()
      .orElse("unknown");
  }

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    var violations = exception.getConstraintViolations().stream()
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
