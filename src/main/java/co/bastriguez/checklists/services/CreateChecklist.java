package co.bastriguez.checklists.services;

import co.bastriguez.checklists.models.Checklist;
import co.bastriguez.security.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
@Transactional
public class CreateChecklist {
  public void create(UUID id, String name, String description, UUID ownerId) {
    var checklist = new Checklist();
    checklist.id = id;
    checklist.name = name;
    checklist.description = description;

    User user = User.findById(ownerId);
    if (user == null) {
      throw new IllegalArgumentException("User with ID " + ownerId + " does not exist");
    }
    checklist.owner = user;

    if (checklist.id == null) {
      throw new IllegalArgumentException("Checklist ID must not be null");
    }

    if (checklist.name == null || checklist.name.isBlank()) {
      throw new IllegalArgumentException("Checklist name must not be blank");
    }

    if (checklist.description == null) {
      checklist.description = "";
    }

    checklist.persist();
  }
}
