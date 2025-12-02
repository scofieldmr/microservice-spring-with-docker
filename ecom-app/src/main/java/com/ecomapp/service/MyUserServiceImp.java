package com.ecomapp.service;

import com.ecomapp.Repository.MyUserRepository;
import com.ecomapp.dto.UserCreateRequest;
import com.ecomapp.dto.UserResponse;
import com.ecomapp.dto.UserUpdateRequest;
import com.ecomapp.entity.Address;
import com.ecomapp.entity.MyUsers;
import com.ecomapp.exception.UserNotFoundException;
import com.ecomapp.mappper.UserMapper;
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
    public Optional<UserResponse> getUserById(long id) {
         MyUsers user = myUserRepository.findById(id).orElse(null);

         if (user == null) {
             throw new UserNotFoundException("User not found with id " + id);
         }
         return Optional.of(userMapper.userToUserResponse(user));
    }

    @Override
    public UserResponse updateUser(long id, UserUpdateRequest updateUserRequest) {
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
