package com.ecomapp.mappper;

import com.ecomapp.dto.AddressDto;
import com.ecomapp.dto.UserCreateRequest;
import com.ecomapp.dto.UserResponse;
import com.ecomapp.entity.Address;
import com.ecomapp.entity.MyUsers;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse userToUserResponse(MyUsers user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setPhone(user.getPhoneNumber());

        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZip(user.getAddress().getZip());
            userResponse.setAddress(addressDto);
        }

        return userResponse;
    }

    public MyUsers userToUserCreate(UserCreateRequest userCreateRequest) {
        MyUsers user = new MyUsers();
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPhoneNumber(userCreateRequest.getPhoneNumber());
        if(userCreateRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userCreateRequest.getAddress().getStreet());
            address.setCity(userCreateRequest.getAddress().getCity());
            address.setState(userCreateRequest.getAddress().getState());
            address.setCountry(userCreateRequest.getAddress().getCountry());
            address.setZip(userCreateRequest.getAddress().getZip());
            user.setAddress(address);
        }

        return user;
    }
}
