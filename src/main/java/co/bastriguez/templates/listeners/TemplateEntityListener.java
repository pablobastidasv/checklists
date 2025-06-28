package co.bastriguez.templates.listeners;

import co.bastriguez.templates.models.Template;
import co.bastriguez.templates.models.DomainEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class TemplateEntityListener {

  private static final Logger logger = Logger.getLogger(TemplateEntityListener.class);

  @Inject
  Event<DomainEvent<?>> events;

  @PostPersist
  public void afterPersist(Template template) {
    logger.debug("Template created: " + template.id + " firing event");
    var event = new DomainEvent<>(
      UUID.randomUUID(),
      template.id,
      DomainEvent.Type.TEMPLATE_CREATED,
      template
    );
    events.fire(event);
  }

}
