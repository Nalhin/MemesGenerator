package com.memes.comment;

import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.shared.utils.CustomModelMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Api(tags = "comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  private final CustomModelMapper customModelMapper;

  @GetMapping(path = "/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get comment by id")
  public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId) {

    return ResponseEntity.ok(customModelMapper.map(commentService.getOneById(commentId), CommentResponseDto.class));
  }

  @PostMapping(path = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Save comment")
  public ResponseEntity<CommentResponseDto> saveComment(
      @RequestBody SaveCommentDto saveCommentDto) {
    Comment savedComment =
        this.commentService.saveComment(customModelMapper.map(saveCommentDto, Comment.class));

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedComment.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(customModelMapper.map(savedComment, CommentResponseDto.class));
  }
}
