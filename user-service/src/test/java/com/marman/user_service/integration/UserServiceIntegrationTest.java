package com.marman.user_service.integration;

import com.marman.user_service.handler.ExceptionResponse;
import com.marman.user_service.service.User;
import com.marman.user_service.service.UserRepository;
import com.marman.user_service.service.UserRequest;
import com.marman.user_service.service.UserResponse;
import com.marman.user_service.testsupport.MySqlTestcontainersBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
public class UserServiceIntegrationTest extends MySqlTestcontainersBase {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser_viaRestApi_persistsAndReturnsUser() {
        UserRequest request = UserRequest.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@gmail.com")
                .address("123 Coding St")
                .alerting(true)
                .energyAlertingThreshold(2000.0)
                .build();

        ResponseEntity<UserResponse> response =
                testRestTemplate.postForEntity("/api/v1/users", request, UserResponse.class);

        var responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.id()).isNotNull();
        assertThat(responseBody.name()).isEqualTo("John");
        assertThat(responseBody.surname()).isEqualTo("Doe");
        assertThat(responseBody.address()).isEqualTo("123 Coding St");
        assertThat(responseBody.email()).isEqualTo("john.doe@gmail.com");
        assertThat(responseBody.alerting()).isTrue();
        assertThat(responseBody.energyAlertingThreshold()).isEqualTo(2000.0);

        ResponseEntity<UserResponse> loaded =
                testRestTemplate.getForEntity("/api/v1/users/" + responseBody.id(), UserResponse.class);

        assertThat(loaded.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loaded.getBody()).isNotNull();
        assertThat(loaded.getBody().email()).isEqualTo("john.doe@gmail.com");
    }

    @Test
    void saveUser_viaRepository_roundTripsThroughMysql() {
        User saved = userRepository.save(User.builder()
                .name("Grace")
                .surname("Hopper")
                .email("grace.it@example.com")
                .address("2 Compiler Way")
                .alerting(false)
                .energyAlertingThreshold(900.0)
                .build());

        assertThat(saved.getId()).isNotNull();

        User fromDb = userRepository.findById(saved.getId()).orElseThrow();
        assertThat(fromDb.getEmail()).isEqualTo("grace.it@example.com");
        assertThat(fromDb.getName()).isEqualTo("Grace");
        assertThat(fromDb.isAlerting()).isFalse();
        assertThat(fromDb.getEnergyAlertingThreshold()).isEqualTo(900.0);
    }


    @Test
    void updateUser_viaRestApi_persistsChanges() {
        UserRequest createRequest = UserRequest.builder()
                .name("Alan")
                .surname("Turing")
                .email("alan.update.it@example.com")
                .address("10 Bletchley Park")
                .alerting(true)
                .energyAlertingThreshold(500.0)
                .build();

        ResponseEntity<UserResponse> created =
                testRestTemplate.postForEntity("/api/v1/users", createRequest, UserResponse.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isNotNull();
        Long id = created.getBody().id();

        UserRequest updateRequest = UserRequest.builder()
                .id(id)
                .name("Alan Mathison")
                .surname("Turing")
                .email("alan.update.it@example.com")
                .address("12 Wilmslow Rd")
                .alerting(false)
                .energyAlertingThreshold(750.0)
                .build();

        ResponseEntity<String> updateResponse = testRestTemplate.exchange(
                "/api/v1/users/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest),
                String.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo("User updated successfully");

        ResponseEntity<UserResponse> loaded =
                testRestTemplate.getForEntity("/api/v1/users/" + id, UserResponse.class);
        var loadedBody = loaded.getBody();

        assertThat(loaded.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loadedBody).isNotNull();
        assertThat(loadedBody.name()).isEqualTo("Alan Mathison");
        assertThat(loadedBody.address()).isEqualTo("12 Wilmslow Rd");
        assertThat(loadedBody.alerting()).isFalse();
        assertThat(loadedBody.energyAlertingThreshold()).isEqualTo(750.0);
    }

    @Test
    void deleteUser_viaRestApi_removesUser() {
        UserRequest createRequest = UserRequest.builder()
                .name("Edsger")
                .surname("Dijkstra")
                .email("edsger.delete.it@example.com")
                .address("3 Structured Programming Ln")
                .alerting(false)
                .energyAlertingThreshold(300.0)
                .build();

        ResponseEntity<UserResponse> created =
                testRestTemplate.postForEntity("/api/v1/users", createRequest, UserResponse.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isNotNull();
        Long id = created.getBody().id();

        ResponseEntity<Void> deleteResponse = testRestTemplate.exchange(
                "/api/v1/users/" + id,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ExceptionResponse> afterDelete =
                testRestTemplate.getForEntity("/api/v1/users/" + id, ExceptionResponse.class);
        assertThat(afterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
