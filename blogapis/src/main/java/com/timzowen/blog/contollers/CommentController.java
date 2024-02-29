package com.timzowen.blog.contollers;

import com.timzowen.blog.payload.CommentDto;
import com.timzowen.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    //create comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
}
