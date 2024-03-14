package com.timzowen.blog.contollers;

import com.timzowen.blog.payload.LoginDto;
import com.timzowen.blog.payload.RegisterDto;
import com.timzowen.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping(value = {"/login", "signin"})
    public ResponseEntity<String> userLogin(@RequestBody LoginDto loginDto){
        String loginUser = authService.login(loginDto);
        return ResponseEntity.ok(loginUser);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){
        String registerUser = authService.registerUser(registerDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }
}
