package co.bastriguez.checklists.controllers;

import co.bastriguez.checklists.models.DomainEvent;
import co.bastriguez.checklists.services.EventsService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("/checklists/events")
public class GetChecklistsEvents {

  @Inject
  EventsService eventsService;

  @GET
  @RestStreamElementType(MediaType.APPLICATION_JSON)
  @Authenticated
  public Multi<DomainEvent<?>> getEvents() {
    return eventsService.events();
  }

}
