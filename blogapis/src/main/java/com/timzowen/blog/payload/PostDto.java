package com.timzowen.blog.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private String content;


}
