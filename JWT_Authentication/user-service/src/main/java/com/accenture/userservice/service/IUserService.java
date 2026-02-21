package com.accenture.userservice.service;

import com.accenture.userservice.dto.ResponseDto;
import com.accenture.userservice.dto.UserDto;
import com.accenture.userservice.entity.User;

import java.util.List;

public interface IUserService {

    String saveUser(User user);
    List<User> getAllUser();

//    User getUserById(Integer userId);
//
//    ResponseDto getUserwithDpt(Integer userId);
//
//
//
//    String removeUserById(Integer Id);
//
//    String updateUser(User user);



    //create user
    UserDto createUser(UserDto userDto);

    //get user by email
    UserDto getUserByEmail(String email);

    //update user
    UserDto updateUser(UserDto userDto, String userId);

    //delete user
    void deleteUser(String userId);

    //get user by id
    UserDto getUserById(String userId);

    //get all users
    Iterable<UserDto> getAllUsers();

    // user service se related __
}
