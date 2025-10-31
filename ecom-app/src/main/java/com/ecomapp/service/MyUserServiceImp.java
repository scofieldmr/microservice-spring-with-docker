package com.ecomapp.service;

import com.ecomapp.entity.MyUsers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserServiceImp implements MyUserService {

    List<MyUsers> users = new ArrayList<>();
    private static long counter = 0;


    public MyUserServiceImp() {
        MyUsers user1 = new MyUsers("Leo","Messi");
        user1.setId(++counter);
        MyUsers user2 = new MyUsers("John","Cena");
        user2.setId(++counter);

        users.add(user1);
        users.add(user2);
    }

    @Override
    public List<MyUsers> getAllUsers() {
        return users;
    }

    @Override
    public MyUsers createNewUser(MyUsers user) {
        user.setId(++counter);
        users.add(user);
        return user;
    }

    @Override
    public Optional<MyUsers> getUserById(long id) {
        return users
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public MyUsers updateUser(long id, MyUsers user) {
        return users.stream().filter(u -> u.getId() == id)
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    return existingUser;
                }).orElse(null);
//        Optional<MyUsers> findUser = users.stream()
//                .filter(user1 -> user1.getId() == id)
//                .findFirst();
//
//        var upUser = findUser.get();
//        System.out.println(upUser);
//
//        upUser.setFirstName(user.getFirstName());
//        upUser.setLastName(user.getLastName());
//
//        return upUser;
    }
}
