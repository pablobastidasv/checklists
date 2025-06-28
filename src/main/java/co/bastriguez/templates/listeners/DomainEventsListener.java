package co.bastriguez.templates.listeners;

import co.bastriguez.templates.models.DomainEvent;
import co.bastriguez.templates.services.EventsService;
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

  public void listenTemplateEvents(@Observes(during = TransactionPhase.AFTER_SUCCESS) DomainEvent<?> templateEvent) {
    eventsService.fire(templateEvent);
  }

}
