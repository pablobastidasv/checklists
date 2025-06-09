package co.bastriguez.checklists.listeners;

import co.bastriguez.checklists.models.DomainEvent;
import co.bastriguez.checklists.services.EventsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DomainEventsListener {

  private static final Logger logger = Logger.getLogger(DomainEventsListener.class);

  @Inject
  EventsService eventsService;

  public void listenChecklistEvents(@Observes(during = TransactionPhase.AFTER_SUCCESS) DomainEvent<?> checklistEvent) {
    eventsService.fire(checklistEvent);
  }

}
