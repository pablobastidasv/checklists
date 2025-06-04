package co.bastriguez.checklists.controllers;

import co.bastriguez.security.AliceTestUser;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.json.Json;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class CheckListResourceTest {

  private static class CheckListBuilder {

    String id = UUID.randomUUID().toString();
    String name = "Default Checklist";
    String description = "This is a default checklist";

    public String build() {
      return Json.createObjectBuilder()
        .add("id", id)
        .add("name", name)
        .add("description", description)
        .build().toString();
    }


    public static String build(Consumer<CheckListBuilder> builder) {
      var jsonBuilder = new CheckListBuilder();
      builder.accept(jsonBuilder);
      return jsonBuilder.build();
    }
  }

  @Nested
  public class PostCheckListTests {

    @Test
    void shouldReturn401WhenNotAuthenticated() {
      var checklist = new CheckListBuilder().build();

      given()
        .contentType("application/json")
        .body(checklist)
        .when()
        .post("/api/checklists")
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldReturn201WhenDataIsValid() {
      var checklist = new CheckListBuilder().build();

      given()
        .contentType("application/json")
        .body(checklist)
        .when()
        .post("/api/checklists")
        .then()
        .statusCode(201);
    }

    @Test
    @AliceTestUser
    void shouldReturn400WhenDataIsInvalid() {
      var invalidChecklist = CheckListBuilder.build(builder -> {
        builder.name = ""; // Invalid name
      });

      given()
        .contentType("application/json")
        .body(invalidChecklist)
        .when()
        .post("/api/checklists")
        .then()
        .statusCode(422)
        .body("type", equalTo("validation-error"))
        .body("title", equalTo("Validation Failed"))
        .body("timestamp", notNullValue())
        .body("detail", equalTo("Information is missing or invalid"))
        .body("violations[0].field", equalTo("name"))
        .body("violations[0].rejectedValue", equalTo(""))
        .body("violations[0].message", equalTo("must not be blank"))
      ;
    }

  }

  @Nested
  public class GetCheckListTests {

    @Test
    void shouldReturn401WhenNotAuthenticated() {
      given()
        .when()
        .get("/api/checklists/{id}", UUID.randomUUID().toString())
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldReturn200WhenChecklistExists() {
      var checklistId = createChecklist();

      given()
        .when()
        .get("/api/checklists/{id}", checklistId)
        .then()
        .statusCode(200)
        .body("id", equalTo(checklistId))
        .body("name", equalTo("Existing Checklist"))
        .body("description", equalTo("This checklist already exists"))
        .body("ownerId", equalTo(AliceTestUser.SUB));
    }

    @Test
    @AliceTestUser
    void shouldReturn404WhenChecklistDoesNotExist() {
      var nonExistentId = UUID.randomUUID().toString();

      given()
        .filter(new RequestLoggingFilter())
        .filter(new ResponseLoggingFilter())
        .when()
        .get("/api/checklists/{id}", nonExistentId)
        .then()
        .statusCode(404)
        .body("type", equalTo("not-found"))
        .body("title", equalTo("Resource Not Found"))
        .body("detail", equalTo("Checklist with ID " + nonExistentId + " does not exist"));
    }
  }

  @Nested
  public class GetChecklistsTests {

    @Test
    void shouldReturn401WhenNotAuthenticated() {
      given()
        .when()
        .get("/api/checklists")
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldReturn200WithListOfChecklists() {
      createChecklist();

      given()
        .when()
        .get("/api/checklists")
        .then()
        .statusCode(200)
        .body("items", notNullValue())
        .body("items.size()", greaterThan(0));
    }
  }

  private String createChecklist() {
    var checklistId = UUID.randomUUID().toString();

    var payload = CheckListBuilder.build(builder -> {
      builder.id = checklistId;
      builder.name = "Existing Checklist";
      builder.description = "This checklist already exists";
    });

    given()
      .contentType("application/json")
      .body(payload)
      .when()
      .post("/api/checklists")
      .then()
      .statusCode(201);

    return checklistId;
  }

}
