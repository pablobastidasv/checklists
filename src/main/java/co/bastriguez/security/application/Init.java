package co.bastriguez.security.application;

import co.bastriguez.security.domain.model.Role;
import co.bastriguez.security.domain.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.Set;

@ApplicationScoped
public class Init {

    private static final Logger logger = Logger.getLogger(Init.class);

    @Startup
    @Transactional
    void init() {
        try {
            logger.info("Initializing application");
            var userRole = new Role();
            userRole.name = "USER";
            userRole.description = "User role";
            Role.persist(userRole);

            var user = new User();
            user.email = "alice@email.com";
            user.password = BcryptUtil.bcryptHash("alice");
            user.roles = Set.of(userRole);
            User.persist(user);
        } catch (Exception e) {
            logger.error("Error initializing application", e);
        }
    }

}
