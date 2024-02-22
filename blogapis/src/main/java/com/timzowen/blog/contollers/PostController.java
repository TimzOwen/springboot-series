package com.timzowen.blog.contollers;

import com.timzowen.blog.payload.PostDto;
import com.timzowen.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
       this.postService=postService;
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/all-posts")
    public List<PostDto> getAllPosts(@RequestParam(value = "pageNo",defaultValue = "",required = false) int pageNo,
                                     @RequestParam(value = "pageSize",defaultValue = "10", required = false)int pageSize){
       return postService.getAllPosts(pageSize,pageNo);
    }

    @GetMapping("/{id}/post")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
       return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable long id){
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        return ResponseEntity.ok(postService.deletePostById(id));
    }

}
