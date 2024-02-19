package com.timzowen.blog.service;

import com.timzowen.blog.payload.PostDto;

public interface PostService {

    PostDto createPost(PostDto postDto);
}
