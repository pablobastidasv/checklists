package co.bastriguez;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

  public record Checklist(boolean status, String description) {
  }

  private final List<Checklist> checklists = List.of(
    new Checklist(true, "Checklist 1"),
    new Checklist(false, "Checklist 2"),
    new Checklist(true, "Checklist 3")
  );

  @Path("/checklists")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Checklist> getChecklists() {
    return checklists;
  }

  @Path("/userinfo")
  @GET
  public UserInfoResponse getUserInfo() {
    return new UserInfoResponse("", "", "", "");
  }

  public record UserInfoResponse(String id, String email, String pictureUrl, String fullName) {
  }
}
