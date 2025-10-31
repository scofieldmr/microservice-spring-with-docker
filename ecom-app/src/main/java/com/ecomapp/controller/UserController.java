package com.ecomapp.controller;

import com.ecomapp.entity.MyUsers;
import com.ecomapp.service.MyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
         List<MyUsers> users = myUserService.getAllUsers();
         return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody MyUsers user) {
        MyUsers newUser = myUserService.createNewUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam long id) {
//       MyUsers user = myUserService.getUserById(id);
//       if (user == null) {
//           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//       }
//       return new ResponseEntity<>(user, HttpStatus.OK);

        return myUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody MyUsers updatedUser) {
         MyUsers user = myUserService.updateUser(id, updatedUser);
         return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }




}
