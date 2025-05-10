package com.timzowen.blog.contollers;

import com.timzowen.blog.payload.CommentDto;
import com.timzowen.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    //create comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    //get list of comments of a post
    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
    @GetMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentByPostId(@PathVariable long postId, @PathVariable long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId,commentId));
    }

    //update comment....
    @PutMapping("posts/{postId}/update/{commentId}")
    public ResponseEntity<CommentDto> updateCommentFromPost(@PathVariable long postId, @PathVariable long commentId,
                                                            @Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateCommentById(postId,commentId,commentDto));
    }

    @DeleteMapping("posts/{postId}/delete/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable long postId, @PathVariable long commentId){
        return ResponseEntity.ok(commentService.deleteCommentById(postId, commentId));
    }

    @DeleteMapping("/posts/{postId}/delete/{commentId}")
    public ResponseEntity<String> deleteCommentByUserId(@PathVariable long postId,@PathVariable long commentId){
        return ResponseEntity.ok(commentService.deleteCommentById(postId,commentId));
    }
}
