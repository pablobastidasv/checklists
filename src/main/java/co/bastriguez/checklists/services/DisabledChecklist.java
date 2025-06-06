package co.bastriguez.checklists.services;

import co.bastriguez.checklists.models.Checklist;
import co.bastriguez.failures.models.ResourceNotFound;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class DisabledChecklist {

  private static final Logger logger = Logger.getLogger(DisabledChecklist.class);

  public void delete(UUID id) {
    logger.debug("Disabling checklist with ID: " + id);
    Checklist.findByIdOptional(id)
      .map(Checklist.class::cast)
      .ifPresentOrElse(
        checklist -> {
          checklist.disable();
          checklist.persist();
        },
        () -> {
          logger.debug("Checklist with ID " + id + " not found for disabling");
          throw new ChecklistNotFoundException(id);
        }
      );
  }

  public static class ChecklistNotFoundException extends ResourceNotFound {
    public ChecklistNotFoundException(UUID id) {
      super("Checklist with ID " + id + " does not exist");
    }
  }

}
