package com.memes.auth;

import com.memes.auth.models.AuthenticatedUser;
import com.memes.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public AuthUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findOneByUsername(username)
        .map(
            (user) ->
                AuthenticatedUser.AuthUserBuilder()
                    .username(user.getUsername())
                    .user(user)
                    .password(user.getPassword())
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .authorities(Collections.emptyList())
                    .build())
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
  }
}
