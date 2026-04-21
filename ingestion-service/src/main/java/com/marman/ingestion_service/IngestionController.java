package com.marman.ingestion_service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingestion")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService service;

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public void ingestData(@RequestBody EnergyUsageDto usageDto) {
        service.ingestEnergyUsage(usageDto);
    }
}
