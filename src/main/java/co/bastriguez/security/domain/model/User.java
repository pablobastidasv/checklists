package co.bastriguez.security.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public UUID id;
  @NotBlank
  @Username
  @Column(unique = true)
  public String email;
  @NotBlank
  @Password
  public String password;
  @Roles
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_name")
  )
  public Set<Role> roles;

  @NotBlank
  public String preferredName;
}
