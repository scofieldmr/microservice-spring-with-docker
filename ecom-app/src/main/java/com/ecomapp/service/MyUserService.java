package com.ecomapp.service;

import com.ecomapp.dto.UserCreateRequest;
import com.ecomapp.dto.UserResponse;
import com.ecomapp.dto.UserUpdateRequest;
import com.ecomapp.entity.MyUsers;

import java.util.List;
import java.util.Optional;

public interface MyUserService {

    List<UserResponse> getAllUsers();

    UserResponse createNewUser(UserCreateRequest userCreateRequest);

    Optional<UserResponse> getUserById(long id);

    UserResponse updateUser(long id, UserUpdateRequest userUpdateRequest);

}
