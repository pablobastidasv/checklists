package co.bastriguez;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

  public record Template(boolean status, String description) {
  }

  private final List<Template> templates = List.of(
    new Template(true, "Template 1"),
    new Template(false, "Template 2"),
    new Template(true, "Template 3")
  );

  @Path("/templates")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Template> getTemplates() {
    return templates;
  }

  @Path("/userinfo")
  @GET
  public UserInfoResponse getUserInfo() {
    return new UserInfoResponse("", "", "", "");
  }

  public record UserInfoResponse(String id, String email, String pictureUrl, String fullName) {
  }
}
