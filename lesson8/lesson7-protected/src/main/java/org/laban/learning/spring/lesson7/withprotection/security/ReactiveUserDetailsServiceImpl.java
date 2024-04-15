package org.laban.learning.spring.lesson7.withprotection.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson7.withprotection.service.UserService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return Mono.empty();
        }

        return userService.findUserByName(username)
                .map(AppUserDetails::new)
                .cast(UserDetails.class);
    }
}
