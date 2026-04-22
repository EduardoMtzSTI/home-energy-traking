package com.marman.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlertingEvent {
    private Long userId;
    private String message;
    private double threshold;
    private double energyConsumed;
    private String email;
}
