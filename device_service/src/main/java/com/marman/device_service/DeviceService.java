package com.marman.device_service;

import com.marman.device_service.handler.DeviceNotFoudException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository repository;
    private final DeviceMapper mapper;

    public DeviceResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(()->new DeviceNotFoudException("Device not found with id:"+id));
    }

    public DeviceResponse create(DeviceRequest request) {
        Device device =mapper.toEntity(request);
        Device deviceSaved = repository.save(device);
        return mapper.toResponse(deviceSaved);
    }

    public DeviceResponse update(Long id, DeviceRequest request) {
        repository.findById(id)
                .orElseThrow(()-> new DeviceNotFoudException("Device not found with id:"+id));
        request.setId(id);
        Device device = mapper.toEntity(request);
        Device deviceSaved = repository.save(device);
        return mapper.toResponse(deviceSaved);
    }

    public void deleteDevice(Long id) {
        repository.findById(id)
                .orElseThrow(()-> new DeviceNotFoudException("Device not found with id:"+id));
        repository.deleteById(id);
    }

    public List<DeviceResponse> getAllByUserId(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
