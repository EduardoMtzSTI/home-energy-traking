package com.marman.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public UserResponse create(UserRequest userRequest) {
//        log.info("Creating user:{}",userRequest);
        User user = repository.save(mapper.toEntity(userRequest));
        return mapper.toResponse(user);
    }

    public UserResponse getById(Long id) {
//        log.info("Getting user by id:{}",id);
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

    public void update(Long id, UserRequest request) {
//        log.info("Updating user with id:{}",id);
        repository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
        request.setId(id);
        User user = mapper.toEntity(request);
        repository.save(user);
    }

    public void delete(Long id){
//        log.info("Deleting user with id:{}",id);
        repository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
        repository.deleteById(id);
    }

}
