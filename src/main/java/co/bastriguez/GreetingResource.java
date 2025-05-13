package co.bastriguez;

import io.quarkus.oidc.UserInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    UserInfo userInfo;


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
        var pictureUrl = Optional.ofNullable(userInfo.getString("picture")).map(Objects::toString).orElse("/user-icon.svg");
        var email = Optional.ofNullable(userInfo.getEmail()).map(Objects::toString).orElse("");
        var name = Optional.ofNullable(userInfo.getName()).map(Objects::toString).orElse("");
        return new UserInfoResponse(userInfo.getSubject(), email, pictureUrl, name);
    }

    public record UserInfoResponse(String id, String email, String pictureUrl, String fullName) {
    }
}
