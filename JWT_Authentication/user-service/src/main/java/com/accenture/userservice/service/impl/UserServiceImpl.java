package com.accenture.userservice.service.impl;

import com.accenture.userservice.config.AppConstants;
import com.accenture.userservice.dto.DepartmentDto;
import com.accenture.userservice.dto.ResponseDto;
import com.accenture.userservice.dto.UserDto;
import com.accenture.userservice.entity.Role;
import com.accenture.userservice.entity.User;
import com.accenture.userservice.repository.RoleRepository;
import com.accenture.userservice.repository.UserRepository;
import com.accenture.userservice.service.IUserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private final ModelMapper modelMapper;



    /*
    Method to add the user
     */
    @Override
    public String saveUser(User user) {
     //   userRepository.save(user);
        return "New User Added Successfully";
    }



    /*
    Method to get all the Users
     */
    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        //TODO:
        //assign the default role
        Role role = roleRepository.findByName("ROLE_" + AppConstants.GUEST_ROLE).orElse(null);
        user.getRoles().add(role);


        User savedUser = userRepository.save(user);
        System.out.println("Saved User: " + savedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserDto getUserById(String userId) {
        return null;
    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
