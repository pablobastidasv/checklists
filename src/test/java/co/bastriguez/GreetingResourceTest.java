package co.bastriguez;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.oidc.Claim;
import io.quarkus.test.security.oidc.OidcSecurity;
import io.quarkus.test.security.oidc.UserInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    private static final Logger log = LoggerFactory.getLogger(GreetingResourceTest.class);

    @Test
    @TestSecurity(user = "alice")
    void checklist() {
        given()
                .when().get("api/checklists")
                .then()
                .statusCode(200)
                .body("[0].status", is(true));
    }

    @Test
    @TestSecurity(user = "alice")
    @OidcSecurity(
            claims = {
                    @Claim(key = "sub", value = "alice"),
                    @Claim(key = "picture", value = "https://test.biz/alice.png"),
            },
            userinfo = {
                    @UserInfo(key = "email", value = "alice@test.biz"),
                    @UserInfo(key = "name", value = "Alice Test"),
            }
    )
    void userinfoEndpoint(){
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
    void defaultUserImage() {
        given()
                .when().get("api/userinfo")
                .then()
                .statusCode(200)
                .body("pictureUrl", is("/user-icon.svg"));
    }
}