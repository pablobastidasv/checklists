package co.bastriguez.security.controllers;

import co.bastriguez.security.application.Login;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("auth/login")
@Consumes("application/json")
@Produces("application/json")
public class PostLogin {

  @Inject
  Login login;

  private static final JsonObject invalidCredentialsResponse = Json.createObjectBuilder()
    .add("message", "Invalid credentials")
    .build();

  public record Request(String email, String password) {
  }

  @POST
  public Response login(Request request) {
    return login.login(request.email(), request.password())
      .map(user -> Response.ok(user).build())
      .orElseGet(
        () -> Response.status(Response.Status.UNAUTHORIZED).entity(invalidCredentialsResponse).build()
      );
  }
}
