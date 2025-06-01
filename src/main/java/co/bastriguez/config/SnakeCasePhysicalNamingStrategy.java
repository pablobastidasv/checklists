package co.bastriguez.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

public class SnakeCasePhysicalNamingStrategy implements PhysicalNamingStrategy {
  @Override
  public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return convertToSnakeCase(identifier);
  }

  @Override
  public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return convertToSnakeCase(identifier);
  }

  @Override
  public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return convertToSnakeCase(identifier);
  }

  @Override
  public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return convertToSnakeCase(identifier);
  }

  @Override
  public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return convertToSnakeCase(identifier);
  }

  private Identifier convertToSnakeCase(Identifier identifier) {
    if (identifier == null) {
      return null;
    }

    String name = identifier.getText();
    String snakeCase = name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
    return Identifier.toIdentifier(snakeCase, identifier.isQuoted());
  }
}
