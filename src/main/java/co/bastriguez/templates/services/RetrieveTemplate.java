package co.bastriguez.templates.services;

import co.bastriguez.templates.models.Template;
import co.bastriguez.failures.models.ResourceNotFound;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class RetrieveTemplate {

  public Template retrieve(UUID id) {
    return Template.findActiveById(id)
      .orElseThrow(() -> new TemplateNotFoundException(id));
  }

  public static class TemplateNotFoundException extends ResourceNotFound {
    public TemplateNotFoundException(UUID id) {
      super("Template with ID " + id + " does not exist");
    }
  }
}
