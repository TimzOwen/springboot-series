package com.timzowen.blog.service;

import com.timzowen.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long id, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long id);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateCommentById(long postId, long commentId, CommentDto updatedCommentDto);

    String deleteCommentById(long postId ,long commentId);

}
