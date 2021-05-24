package com.pismo.controller;

import com.pismo.dto.CreateUserDTO;
import com.pismo.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void create() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("user02@email.com");

        HttpEntity<CreateUserDTO> request = new HttpEntity<>(createUserDTO);

        ResponseEntity response = restTemplate.postForEntity(new URL("http://localhost:" + port + "/users").toString(), request, UserDTO.class);

        UserDTO userDTO = (UserDTO) response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(2L, userDTO.getId());
        assertEquals("user02@email.com", userDTO.getEmail());

    }

}
