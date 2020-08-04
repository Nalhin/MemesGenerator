package com.memes.template;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Template {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String url;
}
