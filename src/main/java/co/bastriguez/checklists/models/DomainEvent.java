package co.bastriguez.checklists.models;

import java.util.UUID;

public record DomainEvent<T>(
  UUID id,
  UUID checklistId,
  Type type,
  T content
) {
  public enum Type {
    CHECKLIST_CREATED,
    CHECKLIST_UPDATED,
    CHECKLIST_DELETED,
  }
}
