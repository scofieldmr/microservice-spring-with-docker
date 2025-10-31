package com.ecomapp.Repository;

import com.ecomapp.entity.MyUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUsers,Long> {

}
