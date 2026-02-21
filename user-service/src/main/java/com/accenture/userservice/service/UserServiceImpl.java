package com.accenture.userservice.service;

import com.accenture.userservice.Exception.UserNotFoundException;
import com.accenture.userservice.controller.UserController;
import com.accenture.userservice.dto.DepartmentDto;
import com.accenture.userservice.dto.ResponseDto;
import com.accenture.userservice.dto.UserDto;
import com.accenture.userservice.entity.User;
import com.accenture.userservice.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with the Id " + userId + " does not exist"));
    }

    /*
    Method to add the user
     */
    @Override
    public String saveUser(User user) {
        userRepository.save(user);
        return "New User Added Successfully";
    }

    /*
    Mehod to Remove the user with Id
     */
    @Override
    public String removeUserById(Integer Id) {
        boolean exist = userRepository.existsById(Id);
        if (!exist) {
            throw new UserNotFoundException("User with the Id " + Id + " does not exit");
        }
        userRepository.deleteById(Id);

        return "Employee with ID " + Id + " deleted";
    }

    /*
    Method to get all the Users
     */
    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /*
    Method to update the user
     */
    @Override
    public String updateUser(User user) {
        boolean exist = userRepository.existsById(user.getId());
        if (!exist) {
            return "user with the Id " + user.getId() + " do not exist";
        }
        user = userRepository.save(user);
        return "user with Id " + user.getId() + " updated successfully!";
    }

    /*
    Method to get the user with department details
    Department is another microservice
     */
    @Override
    public ResponseDto getUserwithDpt(Integer userId) {

        // 1️⃣ Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with ID " + userId + " does not exist"));

        UserDto userDto = mapToUser(user);

        DepartmentDto departmentDto = null;

        try {

            ResponseEntity<DepartmentDto> responseEntity =
                    restTemplate.getForEntity(
                            "http://localhost:8081/api/v1/departments/" + user.getDepartmentId(),
                            DepartmentDto.class
                    );

            departmentDto = responseEntity.getBody();

        }
// ✅ Handle 404 only
        catch (HttpClientErrorException.NotFound ex) {

            logger.warn("Department not found for ID: " + user.getDepartmentId());
            departmentDto = null;   // Business case → valid scenario

        }
// ❌ Let other errors propagate (VERY IMPORTANT)
        catch (Exception ex) {

            logger.error("Department service failure: " + ex.getMessage());
            throw ex;   // <-- This triggers CircuitBreaker fallback
        }


        // 5️⃣ Build response
        ResponseDto responseDto = new ResponseDto();
        responseDto.setUser(userDto);
        responseDto.setDepartment(departmentDto);

        return responseDto;
    }


    private UserDto mapToUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }


}
