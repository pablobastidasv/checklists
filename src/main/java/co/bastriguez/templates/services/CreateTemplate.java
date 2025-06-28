package co.bastriguez.templates.services;

import co.bastriguez.templates.models.Template;
import co.bastriguez.security.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
@Transactional
public class CreateTemplate {
  public void create(UUID id, String name, String description, UUID ownerId) {
    var template = new Template();
    template.id = id;
    template.name = name;
    template.description = description;

    User user = User.findById(ownerId);
    if (user == null) {
      throw new IllegalArgumentException("User with ID " + ownerId + " does not exist");
    }
    template.owner = user;

    if (template.id == null) {
      throw new IllegalArgumentException("Template ID must not be null");
    }

    if (template.name == null || template.name.isBlank()) {
      throw new IllegalArgumentException("Template name must not be blank");
    }

    if (template.description == null) {
      template.description = "";
    }

    template.persist();
  }
}
