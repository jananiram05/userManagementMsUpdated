package com.tekarch.userManagementMs.controller;


import com.tekarch.userManagementMs.model.Account;
import com.tekarch.userManagementMs.model.User;
import com.tekarch.userManagementMs.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Request to create user: {}", user.getUsername());
        return new ResponseEntity<>(userServiceImpl.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Received request to fetch user by ID: {}", id);
        return userServiceImpl.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Received request to fetch all users");
        System.out.println("getting userssss");
        return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Received request to update user with ID: {}", id);
        return new ResponseEntity<>(userServiceImpl.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Received request to delete user with ID: {}", id);

        userServiceImpl.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{id}/kyc")
    public ResponseEntity<User> updateKYCStatus(@PathVariable Long id, @RequestParam String kycStatus) {
        logger.info("Received request to update KYC status for user ID: {}", id);
        return new ResponseEntity<>(userServiceImpl.updateKYCStatus(id, kycStatus),HttpStatus.OK);
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId, @RequestBody Account account) {
        logger.info("Received request to create an account for user ID: {}", userId);
        return new ResponseEntity<>(userServiceImpl.createAccount(userId, account),HttpStatus.OK);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
        logger.info("Received request to fetch accounts for user ID: {}", userId);
        return new ResponseEntity<>(userServiceImpl.getAccountByUserId(userId),HttpStatus.OK);
    }

    @GetMapping("/test")
    public String helathCheck(){

        return "successfull";


    }

}

