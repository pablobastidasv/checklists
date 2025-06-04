package co.bastriguez.checklists.services;

import co.bastriguez.checklists.models.Checklist;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListChecklists {

  public record ChecklistItem(
    String id,
    String name,
    String description,
    int itemsCount
  ) {
  }

  public record ListOfChecklists(
    List<ChecklistItem> items
  ) {
  }

  public ListOfChecklists getChecklists() {
    var checklists = Checklist.listSummaries()
      .page(0, 10)
      .stream()
      .map(summary -> new ChecklistItem(
          summary.id().toString(),
          summary.name(),
          summary.description(),
          summary.itemsCount().intValue()
        )
      ).toList();
    return new ListOfChecklists(checklists);
  }


}
