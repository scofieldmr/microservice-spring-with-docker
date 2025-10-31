package com.ecomapp.service;

import com.ecomapp.Repository.MyUserRepository;
import com.ecomapp.entity.MyUsers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserServiceImp implements MyUserService {
    //    List<MyUsers> users = new ArrayList<>();
   //    private static long counter = 0;

    private final MyUserRepository myUserRepository;

    public MyUserServiceImp(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public List<MyUsers> getAllUsers() {
        return myUserRepository.findAll();
    }

    @Override
    public MyUsers createNewUser(MyUsers user) {
        return myUserRepository.save(user);
    }

    @Override
    public Optional<MyUsers> getUserById(long id) {
        return myUserRepository.findById(id);
    }

    @Override
    public MyUsers updateUser(long id, MyUsers user) {
        return myUserRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    myUserRepository.save(existingUser);
                    return existingUser;
                }).orElse(null);
    }
}
