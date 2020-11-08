package com.memes.security;

import com.memes.security.model.AuthenticatedUser;
import com.memes.user.User;
import com.memes.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository
        .findOneByUsername(username)
        .map(this::mapUser)
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
  }

  private AuthenticatedUser mapUser(User user) {
    return AuthenticatedUser.authBuilder()
        .username(user.getUsername())
        .user(user)
        .password(user.getPassword())
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .authorities(Collections.emptyList())
        .build();
  }
}
