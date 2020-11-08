package com.memes.comment;

import com.memes.security.model.AuthenticatedUser;
import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.common.annotations.Authenticated;
import com.memes.common.annotations.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Api(tags = "comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  private final CommentMapper commentMapper;

  @GetMapping(path = "/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get comment by id")
  public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId) {

    return ResponseEntity.ok(
        commentMapper.commentToCommentResponseDto(commentService.getOneById(commentId)));
  }

  @Authenticated
  @PostMapping(path = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Save comment")
  public ResponseEntity<CommentResponseDto> saveComment(
      @Valid @RequestBody SaveCommentDto saveCommentDto) {

    Comment savedComment =
        this.commentService.saveComment(commentMapper.saveCommentDtoToComment(saveCommentDto));

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedComment.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(commentMapper.commentToCommentResponseDto(savedComment));
  }
}
