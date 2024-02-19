package com.timzowen.blog.service.impl;

import com.timzowen.blog.model.Post;
import com.timzowen.blog.payload.PostDto;
import com.timzowen.blog.repository.PostRepository;
import com.timzowen.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    public PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContents(postDto.getContents());
        post.setDescription(postDto.getDescription());

        Post savedPost = postRepository.save(post);

        PostDto postResponse = new PostDto();
        postResponse.setId(savedPost.getId());
        postResponse.setTitle(savedPost.getTitle());
        postResponse.setContents(savedPost.getContents());
        postResponse.setDescription(savedPost.getDescription());

        return postResponse;
    }
}
