package com.timzowen.blog.service.impl;

import com.timzowen.blog.exceptions.BlogAPIException;
import com.timzowen.blog.exceptions.ResourceNotFoundException;
import com.timzowen.blog.model.Comment;
import com.timzowen.blog.model.Post;
import com.timzowen.blog.payload.CommentDto;
import com.timzowen.blog.repository.CommentRepository;
import com.timzowen.blog.repository.PostRepository;
import com.timzowen.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Comment comment = getCommentByIdAndPostId(postId,commentId);
        return mapToDto(comment);t
    }

    // update comment....
    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto existingComment) {
        Comment comment = getCommentByIdAndPostId(postId,commentId);
        comment.setBody(existingComment.getBody());
        comment.setEmail(existingComment.getEmail());
        comment.setBody(existingComment.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public String deleteCommentById(long postId, long commentId) {
        getCommentByIdAndPostId(postId,commentId);
        commentRepository.deleteById(commentId);
        return "comment deleted with id: " + commentId;
    }

    private Comment getCommentByIdAndPostId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "No such comment associated with a post.");
        }
        return comment;
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
