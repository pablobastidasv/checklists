package co.bastriguez.templates.services;

import co.bastriguez.templates.models.Template;
import co.bastriguez.failures.models.ResourceNotFound;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class DisabledTemplate {

  private static final Logger logger = Logger.getLogger(DisabledTemplate.class);

  public void delete(UUID id) {
    logger.debug("Disabling template with ID: " + id);
    Template.findByIdOptional(id)
      .map(Template.class::cast)
      .ifPresentOrElse(
        template -> {
          template.disable();
          template.persist();
        },
        () -> {
          logger.debug("Template with ID " + id + " not found for disabling");
          throw new TemplateNotFoundException(id);
        }
      );
  }

  public static class TemplateNotFoundException extends ResourceNotFound {
    public TemplateNotFoundException(UUID id) {
      super("Template with ID " + id + " does not exist");
    }
  }

}
