package com.timzowen.blog.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String title;
    private String contents;
    private String description;
    private Set<CommentDto> comments;

}
