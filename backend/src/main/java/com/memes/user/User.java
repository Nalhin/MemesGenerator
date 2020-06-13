package com.memes.user;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String username;

  private String email;

  private String password;
}
