package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

  public String login(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    return jwtService.sign(username);
  }

  public String register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.save(user);
    return jwtService.sign(user.getUsername());
  }
}
