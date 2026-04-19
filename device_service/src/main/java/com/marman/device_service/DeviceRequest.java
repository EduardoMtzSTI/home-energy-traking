package com.marman.device_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest {
    private Long id;
    private String name;
    private DeviceType type;
    private String location;
    private Long userId;
}
