package co.bastriguez.checklists.models;

import co.bastriguez.security.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

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
}
