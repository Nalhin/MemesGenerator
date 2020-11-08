package com.memes.meme;

import com.memes.template.Template;
import com.memes.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Meme {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @CreatedBy @ManyToOne @JoinColumn private User author;

  @ManyToOne @JoinColumn private Template template;

  private String filename;

  @CreationTimestamp private LocalDate created;
}
