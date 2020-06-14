package com.memes.meme;

import com.memes.template.Template;
import com.memes.user.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Meme {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne @JoinColumn private User author;

  @ManyToOne @JoinColumn private Template template;

  private String url;

  @CreationTimestamp private Date created;
}
