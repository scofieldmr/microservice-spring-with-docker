package com.ecomapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyUsers {
    private long id;
    private String firstName;
    private String lastName;

    public MyUsers(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
