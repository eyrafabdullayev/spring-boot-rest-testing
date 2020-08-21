package com.example.springbootrestunittesting.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.springbootrestunittesting.security.ApplicationUserPermission.EMPLOYEE_READ;
import static com.example.springbootrestunittesting.security.ApplicationUserPermission.EMPLOYEE_WRITE;

public enum ApplicationUserRole {

    USER(Sets.newHashSet(EMPLOYEE_READ)),
    ADMIN(Sets.newHashSet(EMPLOYEE_READ, EMPLOYEE_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
