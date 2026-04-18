package com.marman.user_service;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private boolean alerting;
    private double energyAlertingThreshold;
}
