package com.accenture.userservice.service;

import com.accenture.userservice.dto.ResponseDto;
import com.accenture.userservice.entity.User;

import java.util.List;

public interface IUserService {

    String saveUser(User user);

    User getUserById(Integer userId);

    ResponseDto getUserwithDpt(Integer userId);

    List<User> getAllUser();

    String removeUserById(Integer Id);

    String updateUser(User user);
}
