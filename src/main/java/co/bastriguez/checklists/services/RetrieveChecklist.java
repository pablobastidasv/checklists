package co.bastriguez.checklists.services;

import co.bastriguez.checklists.models.Checklist;
import co.bastriguez.failures.models.ResourceNotFound;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class RetrieveChecklist {

  public Checklist retrieve(UUID id) {
    return Checklist.findByIdOptional(id)
      .map(Checklist.class::cast)
      .orElseThrow(() -> new ChecklistNotFoundException(id));
  }

  public static class ChecklistNotFoundException extends ResourceNotFound {
    public ChecklistNotFoundException(UUID id) {
      super("Checklist with ID " + id + " does not exist");
    }
  }
}
