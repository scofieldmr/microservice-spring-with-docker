package com.ms.userservice.repository;

import com.ms.userservice.entity.MyUsers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends MongoRepository<MyUsers,String> {

}
