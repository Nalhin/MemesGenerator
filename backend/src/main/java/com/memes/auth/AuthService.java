package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  public AuthService(
      JwtService jwtService,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder,
      UserService userService) {
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  public Pair<User, String> login(String username, String password) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
    User user = ((AuthUser) auth.getPrincipal()).getUser();
    return Pair.of(user, jwtService.sign(user.getUsername()));
  }

  public Pair<User, String> signUp(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userService.save(user);
    return Pair.of(savedUser, jwtService.sign(user.getUsername()));
  }
}
