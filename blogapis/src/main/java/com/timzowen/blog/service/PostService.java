package com.timzowen.blog.service;

import com.timzowen.blog.payload.PostDto;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;


public interface PostService {

    PostDto createPost(PostDto postDto);
}
