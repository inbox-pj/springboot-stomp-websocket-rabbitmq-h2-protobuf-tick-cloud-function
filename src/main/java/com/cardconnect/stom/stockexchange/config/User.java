package com.cardconnect.stom.stockexchange.config;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

public class User implements Principal {
    private final String username;
    private final String role;

    public interface Roles {
        String ADMIN = "ADMIN";
        String ADD = "ADD";
        String FETCH = "FETCH";
        String DELETE = "DELETE";

        List<String> VALID_ROLES = List.of(Roles.ADMIN, Roles.ADD, Roles.FETCH, Roles.DELETE);

        static boolean isValid(String role) {
            return VALID_ROLES.contains(role);
        }

    }

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }

    @Override
    public String toString() {
        return String.format("User{username='%s', role='%s'}", username, role);
    }
}