package co.bastriguez;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@Disabled
class GreetingResourceTest {

    private static final Logger log = LoggerFactory.getLogger(GreetingResourceTest.class);

    @Test
    @TestSecurity(user = "alice")
    void template() {
        given()
                .when().get("api/templates")
                .then()
                .statusCode(200)
                .body("[0].status", is(true));
    }

    @Test
    @TestSecurity(user = "alice")
    @DisplayName("should return user info")
    void userinfoEndpoint() {
        given()
                .when().get("api/userinfo")
                .then()
                .statusCode(200)
                .body("id", is("alice"))
                .body("email", is("alice@test.biz"))
                .body("fullName", is("Alice Test"))
                .body("pictureUrl", is("https://test.biz/alice.png"));
    }

    @Test
    @TestSecurity(user = "alice")
    @DisplayName("should returned default user image if no picture claim")
    void defaultUserImage() {
        given()
                .when().get("api/userinfo")
                .then()
                .statusCode(200)
                .body("pictureUrl", is("/user-icon.svg"));
    }
}
