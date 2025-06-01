package co.bastriguez.checklists.controllers;

import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;

import java.lang.annotation.*;

@TestSecurity(user = "alice")
@JwtSecurity(
  claims = {
    @Claim(key = "sub", value = AliceTestUser.SUB),
  }
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AliceTestUser {
  String SUB = "0114dd8a-498c-4232-96f0-743e3345b44e";
}
