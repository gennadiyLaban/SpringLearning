package org.laban.learning.spring.lesson4.withprotection.security;

import lombok.Getter;
import org.laban.learning.spring.lesson4.withprotection.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class AppUserDetails implements UserDetails {
    private final Collection<? extends GrantedAuthority> authorities;
    private final String password;
    private final String username;

    public AppUserDetails(User user) {
        authorities = user.getRoles().stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        password = user.getPassword();
        username = user.getUsername();
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
