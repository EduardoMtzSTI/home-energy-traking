package com.marman.device_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService service;

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable Long id) {
        DeviceResponse device = service.getById(id);
        return ResponseEntity.ok(device);
    }

    @PostMapping("/create")
    public ResponseEntity<DeviceResponse> createDevice(@RequestBody DeviceRequest deviceDto) {
        DeviceResponse createdDevice = service.create(deviceDto);
        return ResponseEntity.ok(createdDevice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(
            @PathVariable Long id, @RequestBody DeviceRequest deviceDto) {
        DeviceResponse updatedDevice = service.update(id, deviceDto);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        service.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceResponse>> getAllDevicesByUserId(
            @PathVariable Long userId) {
        List<DeviceResponse> devices = service.getAllByUserId(userId);
        return ResponseEntity.ok(devices);
    }
}
