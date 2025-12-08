package com.ms.userservice.service;


import com.ms.userservice.dto.UserCreateRequest;
import com.ms.userservice.dto.UserResponse;
import com.ms.userservice.dto.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface MyUserService {

    List<UserResponse> getAllUsers();

    UserResponse createNewUser(UserCreateRequest userCreateRequest);

    Optional<UserResponse> getUserById(long id);

    UserResponse updateUser(long id, UserUpdateRequest userUpdateRequest);

}
