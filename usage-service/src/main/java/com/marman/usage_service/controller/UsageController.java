package com.marman.usage_service.controller;

import com.marman.usage_service.dto.UsageDto;
import com.marman.usage_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usage")
@RequiredArgsConstructor
public class UsageController {

    private final UsageService service;

    @GetMapping("/{userId}")
    public ResponseEntity<UsageDto> getUserDeviceUsage(@PathVariable Long userId,
                                                       @RequestParam(defaultValue = "3") int days) {
        final UsageDto usage = service.getXDaysUsageForUser(userId, days);
        return ResponseEntity.ok(usage);
    }

}
