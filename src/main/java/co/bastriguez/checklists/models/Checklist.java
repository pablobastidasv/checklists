package co.bastriguez.checklists.models;

import co.bastriguez.security.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;
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

  @RegisterForReflection
  public record Summary(
    UUID id,
    String name,
    String description,
    Long itemsCount
  ) {
  }

  public static PanacheQuery<Summary> listSummaries() {
    return find("""
      SELECT c.id, c.name, c.description, COUNT(i)
      FROM Checklist c
      LEFT JOIN c.items i
      GROUP BY c.id, c.name, c.description
      """).project(Summary.class);
  }
}
