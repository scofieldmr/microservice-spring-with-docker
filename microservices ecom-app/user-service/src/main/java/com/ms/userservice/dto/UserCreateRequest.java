package com.ms.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AddressDto address;
}
