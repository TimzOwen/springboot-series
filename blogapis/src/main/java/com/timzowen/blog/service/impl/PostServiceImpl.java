package com.timzowen.blog.service.impl;

import com.timzowen.blog.exceptions.ResourceNotFoundException;
import com.timzowen.blog.model.Post;
import com.timzowen.blog.payload.PostDto;
import com.timzowen.blog.payload.PostResponse;
import com.timzowen.blog.repository.PostRepository;
import com.timzowen.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    public PostRepository postRepository;
    public ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper){
        this.modelMapper=modelMapper;
        this.postRepository=postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
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
        return modelMapper.map(post,PostDto.class);
    }

    // convert DTo to Entity
    public Post mapToEntity(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }
}
