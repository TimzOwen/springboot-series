package com.timzowen.blog.service;

import com.timzowen.blog.model.Post;
import com.timzowen.blog.payload.PostDto;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();
}
