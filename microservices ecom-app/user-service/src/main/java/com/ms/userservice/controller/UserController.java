package com.ms.userservice.controller;


import com.ms.userservice.dto.UserCreateRequest;
import com.ms.userservice.dto.UserResponse;
import com.ms.userservice.dto.UserUpdateRequest;
import com.ms.userservice.service.MyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final MyUserService myUserService;

    public UserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
         List<UserResponse> users = myUserService.getAllUsers();
         return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserCreateRequest userCreateRequest) {
        UserResponse newUser = myUserService.createNewUser(userCreateRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam String id) {
        return myUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable("userId") String userId) {
        return myUserService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest updatedUser) {
         UserResponse updateUser = myUserService.updateUser(id, updatedUser);
         return updateUser != null ? ResponseEntity.ok(updateUser) : ResponseEntity.notFound().build();
    }


}
