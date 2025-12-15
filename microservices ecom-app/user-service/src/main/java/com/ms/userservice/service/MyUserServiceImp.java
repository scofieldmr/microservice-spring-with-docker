package com.ms.userservice.service;


import com.ms.userservice.dto.UserCreateRequest;
import com.ms.userservice.dto.UserResponse;
import com.ms.userservice.dto.UserUpdateRequest;
import com.ms.userservice.entity.Address;
import com.ms.userservice.entity.MyUsers;
import com.ms.userservice.exceptions.UserNotFoundException;
import com.ms.userservice.mapper.UserMapper;
import com.ms.userservice.repository.MyUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserServiceImp implements MyUserService {
    //    List<MyUsers> users = new ArrayList<>();
   //    private static long counter = 0;

    private final MyUserRepository myUserRepository;

    private final UserMapper userMapper;

    public MyUserServiceImp(MyUserRepository myUserRepository, UserMapper userMapper) {
        this.myUserRepository = myUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return myUserRepository.findAll().stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse createNewUser(UserCreateRequest userCreateRequest) {
         MyUsers newUser = userMapper.userToUserCreate(userCreateRequest);

         MyUsers savedUser = myUserRepository.save(newUser);

         return userMapper.userToUserResponse(savedUser);
    }

    @Override
    public Optional<UserResponse> getUserById(String id) {
         MyUsers user = myUserRepository.findById(id).orElse(null);

         if (user == null) {
             throw new UserNotFoundException("User not found with id " + id);
         }
         return Optional.of(userMapper.userToUserResponse(user));
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest updateUserRequest) {
        MyUsers user = myUserRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found with id " + id);
        }

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setEmail(updateUserRequest.getEmail());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        Address address = user.getAddress();
        if (address != null) {
            address.setCity(updateUserRequest.getAddress().getCity());
            address.setState(updateUserRequest.getAddress().getState());
            address.setStreet(updateUserRequest.getAddress().getStreet());
            address.setCountry(updateUserRequest.getAddress().getCountry());
            address.setZip(updateUserRequest.getAddress().getZip());
        }
        user.setAddress(address);

        MyUsers updatedUser = myUserRepository.save(user);

        return userMapper.userToUserResponse(updatedUser);
    }
}
