package com.ms.orderservice.client.user;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserClient {

    @GetExchange("/api/v1/user/get/{userId}")
    UserResponse getUserDetails(@PathVariable("userId") String userId);
}
