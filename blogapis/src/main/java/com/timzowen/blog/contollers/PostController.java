package com.timzowen.blog.contollers;

import com.timzowen.blog.payload.PostDto;
import com.timzowen.blog.payload.PostResponse;
import com.timzowen.blog.service.PostService;
import com.timzowen.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
       this.postService=postService;
    }


    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
                                    @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                    @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir){
       return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    // Get post by id.
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }



    @PutMapping("/{id}/update")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id){
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        return ResponseEntity.ok(postService.deletePostById(id));
    }

}
