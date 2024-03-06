package com.timzowen.blog.contollers;

import com.timzowen.blog.payload.CommentDto;
import com.timzowen.blog.service.CommentService;
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
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    //get list of comments of a post
    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}
