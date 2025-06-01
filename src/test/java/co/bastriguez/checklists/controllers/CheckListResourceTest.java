package co.bastriguez.checklists.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class CheckListResourceTest {

  private static class CheckListBuilder {
    private static JsonObjectBuilder jsonBuilder() {
      return Json.createObjectBuilder()
        .add("id", UUID.randomUUID().toString())
        .add("name", "Default Checklist")
        .add("description", "This is a default checklist");
    }

    public static String build() {
      return jsonBuilder()
        .build()
        .toString();
    }


    public static String build(Consumer<JsonObjectBuilder> builder) {
      var jsonBuilder = jsonBuilder();
      builder.accept(jsonBuilder);
      return jsonBuilder.build().toString();
    }
  }

  @Nested
  public class PostCheckListTests {

    @Test
    @AliceTestUser
    void shouldReturn201WhenDataIsValid() {
      var checklist = CheckListBuilder.build();

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
      var invalidChecklist = Json.createObjectBuilder()
        .add("id", UUID.randomUUID().toString())
        .add("name", "") // Invalid: name is empty
        .add("description", "This is a test checklist")
        .build();

      given()
        .contentType("application/json")
        .body(invalidChecklist.toString())
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
    @AliceTestUser
    void shouldReturn200WhenChecklistExists() {
      var checklistId = UUID.randomUUID().toString();

      var payload = CheckListBuilder.build(builder -> builder.add("id", checklistId)
        .add("name", "Existing Checklist")
        .add("description", "This checklist already exists"));

      // create a checklist first
      given()
        .contentType("application/json")
        .body(payload)
        .when()
        .post("/api/checklists")
        .then()
        .statusCode(201);

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

}
