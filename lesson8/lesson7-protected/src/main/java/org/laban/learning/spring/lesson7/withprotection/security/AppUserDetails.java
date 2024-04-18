package org.laban.learning.spring.lesson7.withprotection.security;

import lombok.Getter;
import org.laban.learning.spring.lesson7.withprotection.model.RoleType;
import org.laban.learning.spring.lesson7.withprotection.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AppUserDetails implements UserDetails {
    private final String id;
    private final Set<RoleType> roles;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String password;
    private final String username;

    public AppUserDetails(User user) {
        id = user.getId();
        roles = user.getRoles();
        authorities = roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        password = user.getPassword();
        username = user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
