package com.marman.user_service;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request){
        return User.builder()
                .id(request.getId())
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .address(request.getAddress())
                .alerting(request.isAlerting())
                .energyAlertingThreshold(request.getEnergyAlertingThreshold())
                .build();
    }

    public UserResponse toResponse(User entity){
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .alerting(entity.isAlerting())
                .energyAlertingThreshold(entity.getEnergyAlertingThreshold())
                .build();
    }

}
