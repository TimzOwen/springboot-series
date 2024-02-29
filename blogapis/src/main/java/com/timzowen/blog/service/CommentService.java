package com.timzowen.blog.service;

import com.timzowen.blog.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(long id, CommentDto commentDto);
}
