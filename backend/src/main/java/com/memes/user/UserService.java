package com.memes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  public Optional<User> findOneByUsername(String username) {
    return this.userRepository.findOneByUsername(username);
  }

  public void save(User user) {
    this.userRepository.save(user);
  }
}
