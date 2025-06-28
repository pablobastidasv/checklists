package co.bastriguez.templates.projections;

import co.bastriguez.templates.models.Template;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.UUID;

@RegisterForReflection
public record TemplateSummary(
  UUID id,
  String name,
  String description,
  Long itemsCount
) {
  public static PanacheQuery<TemplateSummary> listSummariesBy(
    Template.Status status
  ) {
    return Template.find("""
      SELECT c.id, c.name, c.description, COUNT(i)
      FROM Template c
      LEFT JOIN c.items i
      WHERE c.status = ?1
      GROUP BY c.id, c.name, c.description
      """, status).project(TemplateSummary.class);
  }
}
