package com.accenture.userservice.controller;

import com.accenture.userservice.dto.ResponseDto;
import com.accenture.userservice.dto.UserDto;
import com.accenture.userservice.entity.User;
import com.accenture.userservice.service.IUserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// resilance4j Link :- https://resilience4j.readme.io/docs/getting-started

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;


   // Logger logger = LoggerFactory.getLogger(UserController.class);


    //create user api
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

//  /**
//     * Method to get the user with the given employee id.
//     *
//     * @param userId
//     * @return
//     * http://localhost:8082/api/users/3
//     */
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUser(@PathVariable("id") Integer userId) {
//        logger.info("to get the user with department details "+userId);
//        User user = userService.getUserById(userId);
//        return ResponseEntity.ok(user);
//    }

    /**
     * Method to add the user.
     *
     * @param user The user entity to be saved.
     * @return A response entity containing the result of the save operation.
     * <a href="http://localhost:8082/api/users/addUser">http://localhost:8082/api/users/addUser</a>
     */
    @PostMapping("/addUser")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        String savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

//    /**
//     * Method to get all the users.
//     *
//     * @return
//     */
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        logger.info("to get all the users");
//        List<User> users = userService.getAllUser();
//
//        return ResponseEntity.ok(users);
//    }

//    /**
//     * Method to delete the user with the given employee id.
//     *
//     * @param userId
//     * @return
//     */
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> removeEmployeeById(@PathVariable("id") Integer userId) {
//        logger.info("to delete the user with id"+userId);
//        return ResponseEntity.ok(userService.removeUserById(userId));
//    }



//    /**
//     * Method to update the user with the Id
//     * @param user
//     * @param id
//     * @return
//     */
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable int id) {
//        logger.info("to update the user with id "+id);
//        user.setId(id);
//        return ResponseEntity.ok(userService.updateUser(user));
//    }
//
//    /**
//     * Method to get the user with department details.
//     * Department is another microservice
//     * @param userId
//     * @return
//     * http://localhost:8082/api/users/department/3
//     * To go in fallback method, stop the department service
//     */
//
//   // @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetDepartmentById")
//    @GetMapping("/user/{id}")
//    public ResponseEntity<ResponseDto> getUserWithDptment(@PathVariable("id") Integer userId) {
//        logger.info("to get the user with department details "+userId);
//        ResponseDto responseDto = userService.getUserwithDpt(userId);
//        return ResponseEntity.ok(responseDto);
//    }
//
//    /**
//     * Fallback method should have same return type and parameters as the original method.
//     * @param id
//     * @param throwable
//     * @return
//     */
//
//    public ResponseEntity<ResponseDto> fallbackGetDepartmentById(Integer id, Throwable throwable) {
//        ResponseDto responseDto = new ResponseDto();
//        logger.error("Fallback method called due to: " + throwable.getMessage());
//        responseDto.setMessage("Department and User service is down, please try again later.");
//        responseDto.setDepartment(null);
//        responseDto.setUser(null);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }


//    @PostConstruct
//    public void addUser() {
//        User user = new User();
//        user.setEmail("kapil94d@gmail.com");
//        user.setDepartmentId("IT");
//        user.setId(124L);
//        user.setFirstName("Kritika");
//        user.setLastName("Chaudhary");
//        userService.saveUser(user);
//    }


}
