package com.timzowen.blog.service.impl;

import com.timzowen.blog.exceptions.BlogAPIException;
import com.timzowen.blog.model.Role;
import com.timzowen.blog.model.User;
import com.timzowen.blog.payload.LoginDto;
import com.timzowen.blog.payload.RegisterDto;
import com.timzowen.blog.repository.RoleRepository;
import com.timzowen.blog.repository.UserRepository;
import com.timzowen.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository,RoleRepository roleRepository){
        this.authenticationManager=authenticationManager;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User login success....";
    }

    @Override
    public String registerUser(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) throw new BlogAPIException(HttpStatus.BAD_REQUEST,"User already exists");
        if (userRepository.existsByEmail(registerDto.getEmail())) throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email already exists");

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> role = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        role.add(userRole);
        user.setRoles(role);
        userRepository.save(user);

        return "User registered....Ok";
    }


}
