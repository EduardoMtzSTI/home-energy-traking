package com.marman.device_service;

import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public Device toEntity(DeviceRequest request){
        return Device.builder()
                .id(request.getId())
                .name(request.getName())
                .type(request.getType())
                .location(request.getLocation())
                .userId(request.getUserId())
                .build();
    }

    public DeviceResponse toResponse(Device entity){
        return DeviceResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .location(entity.getLocation())
                .userId(entity.getUserId())
                .build();
    }
}
