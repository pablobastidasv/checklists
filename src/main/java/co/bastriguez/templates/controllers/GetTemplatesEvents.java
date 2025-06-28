package co.bastriguez.templates.controllers;

import co.bastriguez.templates.models.DomainEvent;
import co.bastriguez.templates.services.EventsService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("/templates/events")
public class GetTemplatesEvents {

  @Inject
  EventsService eventsService;

  @GET
  @RestStreamElementType(MediaType.APPLICATION_JSON)
  @Authenticated
  public Multi<DomainEvent<?>> getEvents() {
    return eventsService.events();
  }

}
