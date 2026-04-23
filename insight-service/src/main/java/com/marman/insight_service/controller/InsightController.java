package com.marman.insight_service.controller;

import com.marman.insight_service.dto.InsightDto;
import com.marman.insight_service.service.InsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/insight")
public class InsightController {

    private final InsightService service;

    @GetMapping("/serving-tips/{userId}")
    public ResponseEntity<InsightDto> getSavingTips(@PathVariable Long userId){
        final InsightDto insightDto = service.getSavingsTips(userId);
        return ResponseEntity.ok(insightDto);
    }

    @GetMapping("/overview/{userId}")
    public ResponseEntity<InsightDto> getOverview(@PathVariable Long userId){
        final InsightDto insightDto = service.getOverview(userId);
        return ResponseEntity.ok(insightDto);
    }
}
