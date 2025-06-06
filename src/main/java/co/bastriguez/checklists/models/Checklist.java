package co.bastriguez.checklists.models;

import co.bastriguez.security.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static co.bastriguez.checklists.models.Checklist.Status.ACTIVE;

@Entity
@Table(name = "checklists")
public final class Checklist extends PanacheEntityBase {
  @Id
  public UUID id;
  public String name;
  public String description;
  @ManyToOne
  public User owner;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "checklist")
  public List<Item> items;
  @Enumerated(EnumType.STRING)
  public Status status = ACTIVE;

  public enum Status {
    ACTIVE,
    DISABLED,
  }

  public void disable() {
    status = Status.DISABLED;
  }

  public static Optional<Checklist> findActiveById(UUID id) {
    return find("id = ?1 AND status = ?2", id, ACTIVE).firstResultOptional();
  }
}
