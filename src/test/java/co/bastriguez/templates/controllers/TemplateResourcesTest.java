package co.bastriguez.templates.controllers;

import co.bastriguez.security.AliceTestUser;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.json.Json;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

@QuarkusTest
class TemplateResourcesTest {

  private static final Logger logger = Logger.getLogger(TemplateResourcesTest.class);

  @TestHTTPEndpoint(GetTemplatesEvents.class)
  @TestHTTPResource
  URI sseEventsUri;

  private static class TemplateBuilder {

    String id = UUID.randomUUID().toString();
    String name = "Default Template";
    String description = "This is a default template";

    public String build() {
      return Json.createObjectBuilder()
        .add("id", id)
        .add("name", name)
        .add("description", description)
        .build().toString();
    }


    public static String build(Consumer<TemplateBuilder> builder) {
      var jsonBuilder = new TemplateBuilder();
      builder.accept(jsonBuilder);
      return jsonBuilder.build();
    }
  }

  @Nested
  public class PostTemplateTests {

    @Test
    void shouldReturn401WhenNotAuthenticated() {
      var template = new TemplateBuilder().build();

      given()
        .contentType("application/json")
        .body(template)
        .when()
        .post("/api/templates")
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldReturn201WhenDataIsValid() {
      var template = new TemplateBuilder().build();

      given()
        .contentType("application/json")
        .body(template)
        .when()
        .post("/api/templates")
        .then()
        .statusCode(201);
    }

    @Test
    @AliceTestUser
    void shouldReturn400WhenDataIsInvalid() {
      var invalidTemplate = TemplateBuilder.build(builder -> {
        builder.name = ""; // Invalid name
      });

      given()
        .contentType("application/json")
        .body(invalidTemplate)
        .when()
        .post("/api/templates")
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
  public class GetTemplateTests {

    @Test
    void shouldReturn401WhenNotAuthenticated() {
      given()
        .when()
        .get("/api/templates/{id}", UUID.randomUUID().toString())
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldReturn200WhenTemplateExists() {
      var templateId = createTemplate();

      given()
        .when()
        .get("/api/templates/{id}", templateId)
        .then()
        .statusCode(200)
        .body("id", equalTo(templateId))
        .body("name", equalTo("Existing Template"))
        .body("description", equalTo("This template already exists"))
        .body("ownerId", equalTo(AliceTestUser.SUB));
    }

    @Test
    @AliceTestUser
    void shouldReturn404WhenTemplateDoesNotExist() {
      var nonExistentId = UUID.randomUUID().toString();

      given()
        .when()
        .get("/api/templates/{id}", nonExistentId)
        .then()
        .statusCode(404)
        .body("type", equalTo("not-found"))
        .body("title", equalTo("Resource Not Found"))
        .body("detail", equalTo("Template with ID " + nonExistentId + " does not exist"));
    }
  }

  @Nested
  public class GetTemplatesTests {

    @Test
    void shouldReturn401WhenNotAuthenticated() {
      given()
        .when()
        .get("/api/templates")
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldReturn200WithListOfTemplates() {
      createTemplate();

      given()
        .when()
        .get("/api/templates")
        .then()
        .statusCode(200)
        .body("items", notNullValue())
        .body("items.size()", greaterThan(0));
    }
  }

  @Nested
  public class DeleteTemplates {
    @Test
    void shouldReturn401WhenNotAuthenticated() {
      given()
        .when()
        .delete("/api/templates/{id}", UUID.randomUUID().toString())
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldReturn204WhenTemplateIsDeleted() {
      var templateId = createTemplate();

      given()
        .when()
        .delete("/api/templates/{id}", templateId)
        .then()
        .statusCode(204);
    }

    @Test
    @AliceTestUser
    void shouldReturn422WhenIdIsNotUUID() {
      var invalidId = "not-a-uuid";

      given()
        .when()
        .delete("/api/templates/{id}", invalidId)
        .then()
        .statusCode(422)
        .body("type", equalTo("validation-error"))
        .body("title", equalTo("Validation Failed"))
        .body("timestamp", notNullValue())
        .body("detail", equalTo("Information is missing or invalid"))
        .body("violations[0].field", equalTo("id"))
        .body("violations[0].rejectedValue", equalTo(invalidId))
        .body("violations[0].message", equalTo("must be a valid UUID"));
    }

    @Test
    @AliceTestUser
    void shouldReturn404WhenTemplateDoesNotExist() {
      var nonExistentId = UUID.randomUUID().toString();

      given()
        .when()
        .delete("/api/templates/{id}", nonExistentId)
        .then()
        .statusCode(404)
        .body("type", equalTo("not-found"))
        .body("title", equalTo("Resource Not Found"))
        .body("detail", equalTo("Template with ID " + nonExistentId + " does not exist"));
    }

    @Test
    @AliceTestUser
    void shouldReturn404WhenTemplateIsDisabled() {
      var templateId = createTemplate();

      given()
        .when()
        .delete("/api/templates/{id}", templateId)
        .then()
        .statusCode(204);

      // Verify that the template is disabled
      given()
        .when()
        .get("/api/templates/{id}", templateId)
        .then()
        .statusCode(404)
        .body("type", equalTo("not-found"))
        .body("title", equalTo("Resource Not Found"))
        .body("detail", equalTo("Template with ID " + templateId + " does not exist"));
    }

    @Test
    @AliceTestUser
    void shouldNotShownTheTemplateInTheListAfterDeletion() {
      var templateId = createTemplate();

      // Verify that the template is in the list
      given()
        .filter(new RequestLoggingFilter())
        .filter(new ResponseLoggingFilter())
        .when()
        .get("/api/templates")
        .then()
        .statusCode(200)
        .body("items", notNullValue())
        .body("items.find { it.id == '" + templateId + "' }", notNullValue());

      // Delete the template
      given()
        .when()
        .delete("/api/templates/{id}", templateId)
        .then()
        .statusCode(204);

      // Verify that the template is not in the list
      given()
        .filter(new RequestLoggingFilter())
        .filter(new ResponseLoggingFilter())
        .when()
        .get("/api/templates")
        .then()
        .statusCode(200)
        .body("items", notNullValue())
        .body("items.find { it.id == '" + templateId + "' }", nullValue());
    }
  }

  @Nested
  public class StreamTemplateEvents {


    @Test
    void shouldReturn401WhenNotAuthenticated() {
      given()
        .when()
        .get("/api/templates/events")
        .then()
        .statusCode(401);
    }

    @Test
    @AliceTestUser
    void shouldFiredEventsWhenTemplateCreated() {
      var eventReceived = new AtomicBoolean(false);

      var client = HttpClient.newHttpClient();
      var request = HttpRequest.newBuilder()
        .uri(sseEventsUri)
        .header("Accept", "text/event-stream")
        .build();

      var future = client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
        .thenAccept(res -> res.body().forEach(l -> {
          if (l.contains("TEMPLATE_CREATED")) {
            eventReceived.set(true);
          }
        })).exceptionally(t -> {
          logger.error("Error in SSE stream", t);
          return null;
        });

      createTemplate();

      try {
        await().atMost(1, TimeUnit.SECONDS)
          .pollInterval(200, TimeUnit.MILLISECONDS)
          .until(eventReceived::get)
        ;
      } catch (Exception e) {
        fail("Event was not received within the timeout period", e);
      } finally {
        future.cancel(true);
        client.close();
      }
    }

  }

  private String createTemplate() {
    var templateId = UUID.randomUUID().toString();

    var payload = TemplateBuilder.build(builder -> {
      builder.id = templateId;
      builder.name = "Existing Template";
      builder.description = "This template already exists";
    });

    given()
      .contentType("application/json")
      .body(payload)
      .when()
      .post("/api/templates")
      .then()
      .statusCode(201);

    return templateId;
  }

}
