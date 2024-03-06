package com.timzowen.blog.service;

import com.timzowen.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long id, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long id);
}
