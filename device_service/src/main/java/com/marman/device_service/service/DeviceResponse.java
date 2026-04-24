package com.marman.device_service.service;

import lombok.Builder;

@Builder
public record DeviceResponse(
        Long id,
        String name,
        DeviceType type,
        String location,
        Long userId
) { }
