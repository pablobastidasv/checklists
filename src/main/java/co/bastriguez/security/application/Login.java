package co.bastriguez.security.application;

import co.bastriguez.security.domain.model.User;
import co.bastriguez.security.domain.service.TokenGenerator;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class Login {

  private static final Logger logger = Logger.getLogger(Login.class);

  @Inject
  TokenGenerator tokenGenerator;

  public record LoggedUser(
    String email,
    String token,
    String[] roles
  ) {
  }


  public Optional<LoggedUser> login(String email, String password) {
    User user = User.find("email", email).firstResult();

    if (user == null || !BcryptUtil.matches(password, user.password)) {
      logger.debugf("Invalid credentials for user email %s", email);
      return Optional.empty();
    }

    var tokenClaims = new TokenGenerator.Token(
      user.id.toString(),
      String.valueOf(System.currentTimeMillis() / 1000),
      user.preferredName,
      email,
      user.roles.stream().map(role -> role.name).collect(Collectors.toSet())
    );
    var token = tokenGenerator.generateToken(tokenClaims);

    var loggedUser = new LoggedUser(
      user.email,
      token,
      user.roles.stream().map(role -> role.name).toArray(String[]::new)
    );

    return Optional.of(loggedUser);
  }

}
