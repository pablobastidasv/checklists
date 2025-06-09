package co.bastriguez.checklists.listeners;

import co.bastriguez.checklists.models.Checklist;
import co.bastriguez.checklists.models.DomainEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class ChecklistEntityListener {

  private static final Logger logger = Logger.getLogger(ChecklistEntityListener.class);

  @Inject
  Event<DomainEvent<?>> events;

  @PostPersist
  public void afterPersist(Checklist checklist) {
    logger.debug("Checklist created: " + checklist.id + " firing event");
    var event = new DomainEvent<>(
      UUID.randomUUID(),
      checklist.id,
      DomainEvent.Type.CHECKLIST_CREATED,
      checklist
    );
    events.fire(event);
  }

}
