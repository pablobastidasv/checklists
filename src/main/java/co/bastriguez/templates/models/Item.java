package co.bastriguez.templates.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "template_items")
public final class Item extends PanacheEntityBase {
  @Id
  public UUID id;
  public String description;
  public int position;
  @ManyToOne(optional = false)
  public Template template;
}
