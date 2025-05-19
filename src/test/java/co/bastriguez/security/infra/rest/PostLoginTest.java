package co.bastriguez.security.infra.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class PostLoginTest {

    @Test
    @DisplayName("should return 200 when login is successful with logged user information")
    void successfulLogin() {
        given()
                .body("{\"email\": \"alice@email.com\", \"password\": \"alice\"}")
                .contentType(ContentType.JSON)
                .when().post("api/auth/login")
                .then()
                .statusCode(200)
                .body("email", is("alice@email.com"))
                .body("roles[0]", is("USER"))
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("should return 401 when login is unsuccessful with invalid credentials message")
    void invalidCredentials(){
        given()
                .body("{\"email\": \"alice@email.com\", \"password\": \"bad_credential\"}")
                .contentType(ContentType.JSON)
                .when().post("api/auth/login")
                .then()
                .statusCode(401)
                .body("message", is("Invalid credentials"));
    }

}