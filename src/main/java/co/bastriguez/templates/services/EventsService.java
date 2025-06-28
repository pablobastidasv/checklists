package co.bastriguez.templates.services;

import co.bastriguez.templates.models.DomainEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EventsService {

  private static final Logger logger = Logger.getLogger(EventsService.class.getName());

  private final BroadcastProcessor<DomainEvent<?>> eventProcessor = BroadcastProcessor.create();

  public void fire(DomainEvent<?> event) {
    logger.debug("Publishing event: " + event);
    eventProcessor.onNext(event);
  }

  public Multi<DomainEvent<?>> events() {
    logger.debug("Listening to events");
    return eventProcessor
//      .filter( event -> canAccess) // filter if the requested event is accessible by the user
      .onFailure().retry().atMost(3);
  }
}
