package com.memes.comment;

import com.memes.auth.models.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Comment getOneById(Long commentId) {
    return this.commentRepository
        .findById(commentId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
  }

  public Comment saveComment(Comment comment, AuthenticatedUser user) {
    comment.setAuthor(user.getPresentUser());
    return this.commentRepository.save(comment);
  }
}
