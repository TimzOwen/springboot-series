package com.timzowen.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    @NotEmpty(message = "Name must have more than 2 characters")
    private String name;
    @NotEmpty
    @Size(min = 10, message = "body should have at least 10 characters")
    private String body;
    @NotEmpty(message = "email cannot be empty or invalid")
    @Email
    private String email;
}
