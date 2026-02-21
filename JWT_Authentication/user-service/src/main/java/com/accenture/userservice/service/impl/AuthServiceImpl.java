package com.accenture.userservice.service.impl;

import com.accenture.userservice.dto.UserDto;
import com.accenture.userservice.service.AuthService;
import com.accenture.userservice.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final IUserService userService;
    private  final PasswordEncoder passwordEncoder;


    @Override
    public UserDto registerUser(UserDto userDto) {
        //logic
        //verify email
        //verify password
        //default roles
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));


        return userService.createUser(userDto);
    }
}
