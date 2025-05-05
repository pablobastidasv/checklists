package co.bastriguez;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/")
public class GreetingResource {

    public record Checklist(boolean status, String description) {
    }

    private final List<Checklist> checklists = List.of(
            new Checklist(true, "Checklist 1"),
            new Checklist(false, "Checklist 2"),
            new Checklist(true, "Checklist 3")
    );

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @Path("/checklists")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Checklist> getChecklists() {
        return checklists;
    }
}
