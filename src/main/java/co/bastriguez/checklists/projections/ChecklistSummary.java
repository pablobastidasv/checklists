package co.bastriguez.checklists.projections;

import co.bastriguez.checklists.models.Checklist;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.UUID;

@RegisterForReflection
public record ChecklistSummary(
  UUID id,
  String name,
  String description,
  Long itemsCount
) {
  public static PanacheQuery<ChecklistSummary> listSummaries() {
    return Checklist.find("""
      SELECT c.id, c.name, c.description, COUNT(i)
      FROM Checklist c
      LEFT JOIN c.items i
      GROUP BY c.id, c.name, c.description
      """).project(ChecklistSummary.class);
  }
}
