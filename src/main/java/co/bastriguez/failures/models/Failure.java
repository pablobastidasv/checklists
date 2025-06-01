package co.bastriguez.failures.models;

public record Failure(
  String type,
  String title,
  String detail,
  String timestamp,
  Violation[] violations
) {
  public record Violation(
    String field,
    String rejectedValue,
    String message
  ) {
  }
}
