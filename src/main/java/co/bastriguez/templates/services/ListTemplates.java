package co.bastriguez.templates.services;

import co.bastriguez.templates.models.Template;
import co.bastriguez.templates.projections.TemplateSummary;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListTemplates {

  public record ListOfTemplates(List<TemplateSummary> items) {
  }

  public ListOfTemplates getTemplates() {
    var templates = TemplateSummary.listSummariesBy(Template.Status.ACTIVE).list();
    return new ListOfTemplates(templates);
  }
}
