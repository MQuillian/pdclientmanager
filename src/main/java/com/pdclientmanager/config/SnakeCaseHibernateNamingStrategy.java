package com.pdclientmanager.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SnakeCaseHibernateNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name, true);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name, false);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name, false);
    }
    
    private Identifier convertToSnakeCase(Identifier identifier, boolean isTable) {
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        String newName = identifier.getText()
                .replaceAll(regex, replacement)
                .toLowerCase();
        if(isTable == true) {
            if(newName.endsWith("y") && !newName.endsWith("ey")) {
                newName = newName.substring(0, newName.length() -1);
                newName += "ie";
            }
            newName += "s";
        }
        return Identifier.toIdentifier(newName);
    }

}
