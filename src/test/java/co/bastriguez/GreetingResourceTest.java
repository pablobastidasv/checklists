package co.bastriguez;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestSecurity(user = "alice")
class GreetingResourceTest {

    @Test
    void checklist() {
        given()
                .when().get("checklists")
                .then()
                .statusCode(200)
                .body("[0].status", is(true));
    }

}