package com.timzowen.blog.service.impl;

import com.timzowen.blog.model.Post;
import com.timzowen.blog.payload.PostDto;
import com.timzowen.blog.repository.PostRepository;
import com.timzowen.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    public PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto postResponse = mapToDto(savedPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // convert Entity to DTO
    public PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContents(post.getContents());
        return postDto;
    }

    // convert DTo to Entity
    public Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContents(postDto.getContents());
        post.setDescription(postDto.getDescription());
        return post;
    }
}
