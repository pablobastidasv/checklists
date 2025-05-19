package co.bastriguez.security.domain.service;

import co.bastriguez.security.domain.model.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenGenerator {

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://dev.bastriguez.net/issuer")
    String issuer;

    public String generateToken(User user) {
        return Jwt.issuer(issuer)
                .subject(user.id.toString())
                .groups(user.roles.stream().map(role -> role.name).collect(Collectors.toSet()))
                .claim("email", user.email)
                .expiresIn(Duration.ofHours(24))
                .sign();
    }
}
