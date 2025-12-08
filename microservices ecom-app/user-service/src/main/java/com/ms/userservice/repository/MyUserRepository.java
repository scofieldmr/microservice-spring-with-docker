package com.ms.userservice.repository;

import com.ms.userservice.entity.MyUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUsers,Long> {

}
