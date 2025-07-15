package co.bastriguez.templates.controllers;

import co.bastriguez.templates.models.DomainEvent;
import co.bastriguez.templates.services.EventsService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestStreamElementType;


@Path("/templates/events")
public class GetTemplatesEvents {

  private final static Logger logger = Logger.getLogger(GetTemplatesEvents.class.getName());

  @Inject
  EventsService eventsService;

  @GET
  @RestStreamElementType(MediaType.APPLICATION_JSON)
  @Authenticated
  public Multi<DomainEvent<?>> getEvents() {
    logger.debug("User connecting to templates events stream.");
    return eventsService.events();
  }

}
