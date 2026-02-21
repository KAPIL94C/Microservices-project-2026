package com.accenture.userservice.controller;

import com.accenture.userservice.dto.ResponseDto;
import com.accenture.userservice.entity.User;
import com.accenture.userservice.service.IUserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// resilance4j Link :- https://resilience4j.readme.io/docs/getting-started

@RestController
@RequestMapping("api/v1/emp")
public class UserController {

    @Autowired
    private IUserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);


  /**
     * Method to get the user with the given employee id.
     *
     * @param userId
     * @return
     * http://localhost:8082/api/users/3
     */

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer userId) {
        logger.info("to get the user with department details "+userId);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Method to add the user.
     *
     * @param user
     * @return
     * http://localhost:8082/api/users/addUser
     */
    @PostMapping("/addUser")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        logger.info("to add the useer "+user);
        String savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    /**
     * Method to delete the user with the given employee id.
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeEmployeeById(@PathVariable("id") Integer userId) {
        logger.info("to delete the user with id"+userId);
        return ResponseEntity.ok(userService.removeUserById(userId));
    }

    /**
     * Method to get all the users.
     *
     * @return
     */

    int counter = 0;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("to get all the users");

        counter++;
        if(counter <= 3){

            System.out.println("retried "+counter);
            throw new RuntimeException("Service is down, retrying...");
        }

        List<User> users = userService.getAllUser();

        return ResponseEntity.ok(users);
    }

    /**
     * Method to update the user with the Id
     * @param user
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable int id) {
        logger.info("to update the user with id "+id);
        user.setId(id);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * Method to get the user with department details.
     * Department is another microservice
     * @param userId
     * @return
     * http://localhost:8082/api/users/department/3
     * To go in fallback method, stop the department service
     */

    @RateLimiter(name = "userService", fallbackMethod = "rateLimiterFallback")
    @Retry(name = "userService", fallbackMethod = "fallbackGetDepartmentById")
    @CircuitBreaker(name = "userService")
    @GetMapping("/employee/{id}")
    public ResponseEntity<ResponseDto> getUserWithDptment(@PathVariable("id") Integer userId) {
        logger.info("to get the user with department details "+userId);
        ResponseDto responseDto = userService.getUserwithDpt(userId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Fallback method should have same return type and parameters as the original method.
     * @param id
     * @param throwable
     * @return
     */

    public ResponseEntity<ResponseDto> fallbackGetDepartmentById(Integer id, Throwable throwable) {
        ResponseDto responseDto = new ResponseDto();
        logger.error("Fallback method called due to: " + throwable.getMessage());
        responseDto.setMessage("Department and User service is down, please try again later.");
        responseDto.setDepartment(null);
        responseDto.setUser(null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> rateLimiterFallback(Integer id, RequestNotPermitted ex) {
        ResponseDto dto = new ResponseDto();
        dto.setMessage("Too many requests. Please try again later.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(dto);
    }


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
