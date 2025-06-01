package co.bastriguez.security.domain.service;

import co.bastriguez.security.domain.model.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenGenerator {

  @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://dev.bastriguez.net/issuer")
  String issuer;

  public String generateToken(Token token) {
    return Jwt.issuer(issuer)
      .subject(token.sub)
      .groups(token.roles)
      .claim("email", token.email)
      .expiresIn(Duration.ofHours(24))
      .sign();
  }

  public record Token(
    String sub,
    String iat,
    String preferred_username,
    String email,
    Set<String> roles
  ) {
  }

}
