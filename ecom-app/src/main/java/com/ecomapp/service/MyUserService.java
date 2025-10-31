package com.ecomapp.service;

import com.ecomapp.entity.MyUsers;

import java.util.List;
import java.util.Optional;

public interface MyUserService {

    List<MyUsers> getAllUsers();

    MyUsers createNewUser(MyUsers user);

    Optional<MyUsers> getUserById(long id);

    MyUsers updateUser(long id, MyUsers user);

}
