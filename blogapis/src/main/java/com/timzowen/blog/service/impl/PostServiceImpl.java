package com.timzowen.blog.service.impl;

import com.timzowen.blog.exceptions.ResourceNotFoundException;
import com.timzowen.blog.model.Post;
import com.timzowen.blog.payload.PostDto;
import com.timzowen.blog.repository.PostRepository;
import com.timzowen.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<PostDto> getAllPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public String deletePostById(long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        mapToDto(post);
        postRepository.deleteById(id);
        return "post delete successfully Id:" + id;
    }

    public PostDto updatePost(PostDto postDto, long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setContents(postDto.getContents());
        post.setDescription(postDto.getDescription());
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    // convert Entity to DTO
    public PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContents(post.getContents());
        postDto.setDescription(post.getDescription());
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
