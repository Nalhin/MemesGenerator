package com.memes.auth;

import com.memes.security.model.AuthenticatedUser;
import com.memes.jwt.JwtService;
import com.memes.jwt.model.JwtPayload;
import com.memes.user.User;
import com.memes.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public Pair<User, JwtPayload> login(String username, String password) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
    User user = ((AuthenticatedUser) auth.getPrincipal()).getUser();
    return Pair.of(user, jwtService.sign(user.getUsername()));
  }

  public Pair<User, JwtPayload> signUp(User user) {
    if (userRepository.existsByEmailOrUsername(user.getEmail(), user.getUsername())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username or email is already taken");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    return Pair.of(savedUser, jwtService.sign(user.getUsername()));
  }
}
