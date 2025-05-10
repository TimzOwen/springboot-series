package com.timzowen.blog.service;

import com.timzowen.blog.payload.LoginDto;
import com.timzowen.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String registerUser(RegisterDto registerDto);
}
