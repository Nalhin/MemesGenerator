package com.memes.comment.test;

import com.github.javafaker.Faker;
import com.memes.comment.Comment;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.user.test.UserTestFactory;

import java.time.LocalDate;

public class CommentTestFactory {

  private static final Faker faker = new Faker();

  public static Comment.CommentBuilder comment() {
    return Comment.builder()
        .id(faker.number().numberBetween(0, Long.MAX_VALUE))
        .content(faker.lorem().sentence(10));
  }

  public static Comment.CommentBuilder commentWithAuthor() {
    return comment().content(faker.lorem().sentence(10)).author(UserTestFactory.user().build());
  }

  public static SaveCommentDto.SaveCommentDtoBuilder saveCommentDto() {
    return SaveCommentDto.builder().content(faker.lorem().sentence(10));
  }
}
