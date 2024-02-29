package com.timzowen.blog.service.impl;

import com.timzowen.blog.exceptions.ResourceNotFoundException;
import com.timzowen.blog.model.Comment;
import com.timzowen.blog.model.Post;
import com.timzowen.blog.payload.CommentDto;
import com.timzowen.blog.repository.CommentRepository;
import com.timzowen.blog.repository.PostRepository;
import com.timzowen.blog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        comment.setPost(post); // set post to comment entity
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        return comment;
    }
}
