package com.timzowen.blog.service;

import com.timzowen.blog.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
