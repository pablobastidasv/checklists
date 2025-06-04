package co.bastriguez.checklists.services;

import co.bastriguez.checklists.projections.ChecklistSummary;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListChecklists {

  public record ListOfChecklists(List<ChecklistSummary> items) {
  }

  public ListOfChecklists getChecklists() {
    var checklists = ChecklistSummary.listSummaries().list();
    return new ListOfChecklists(checklists);
  }
}
