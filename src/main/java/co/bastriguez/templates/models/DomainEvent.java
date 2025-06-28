package co.bastriguez.templates.models;

import java.util.UUID;

public record DomainEvent<T>(
  UUID id,
  UUID templateId,
  Type type,
  T content
) {
  public enum Type {
    TEMPLATE_CREATED,
    TEMPLATE_UPDATED,
    TEMPLATE_DELETED,
  }
}
