package com.marman.usage_service.client;

import com.marman.usage_service.dto.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DeviceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public DeviceClient(@Value("${device.service.url}") String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    public DeviceDto getDeviceById(Long id){
        String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/{id}")
                .buildAndExpand(id)
                .toString();

        ResponseEntity<DeviceDto> response = restTemplate.getForEntity(url,DeviceDto.class);
        return response.getBody();
    }
}
