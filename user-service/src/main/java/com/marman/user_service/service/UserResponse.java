package com.marman.user_service.service;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String name,
        String surname,
        String email,
        String address,
        boolean alerting,
        double energyAlertingThreshold
) { }
