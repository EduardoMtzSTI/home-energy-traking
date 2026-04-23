package com.marman.insight_service.dto;

import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
public record DeviceDto(
        Long id,
        String name,
        String type,
        String location,
        double energyConsumed
) { }
