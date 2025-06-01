package co.bastriguez.failures.models;

public class ResourceNotFound extends RuntimeException {
  public ResourceNotFound(String message) {
    super(message);
  }
}
