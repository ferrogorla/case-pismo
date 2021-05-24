package com.pismo.controller;

import com.pismo.dto.AccountDTO;
import com.pismo.dto.CreateAccountDTO;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void create() throws Exception {
        UserDTO userDTO = createUser();

        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setUserId(1L);
        createAccountDTO.setDocumentNumber(12345678900L);

        HttpEntity<CreateAccountDTO> request = new HttpEntity<>(createAccountDTO);

        ResponseEntity response = restTemplate.postForEntity(new URL("http://localhost:" + port + "/accounts").toString(), request, AccountDTO.class);

        AccountDTO accountDTO = (AccountDTO) response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, accountDTO.getId());
        assertEquals(1L, userDTO.getId());
        assertEquals(12345678900L, accountDTO.getDocumentNumber());
        assertEquals(500D, accountDTO.getBalance());
        assertEquals(null, accountDTO.getTransactions());

    }

    @Test
    public void getAll() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(new URL("http://localhost:" + port + "/accounts?userId=1&pageNumber=0&pageSize=5").toString(), ArrayList.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<AccountDTO>) response.getBody()).size());
    }

    @Test
    public void getById() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(new URL("http://localhost:" + port + "/accounts/1").toString(), AccountDTO.class);

        AccountDTO accountDTO = (AccountDTO) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, accountDTO.getId());
        assertEquals(1L, accountDTO.getUserId());
        assertEquals(12345678900L, accountDTO.getDocumentNumber());
        assertEquals(500D, accountDTO.getBalance());
        assertEquals(null, accountDTO.getTransactions());
    }

    private UserDTO createUser() throws MalformedURLException {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("user01@email.com");

        HttpEntity<CreateUserDTO> request = new HttpEntity<>(createUserDTO);
        ResponseEntity response = restTemplate.postForEntity(new URL("http://localhost:" + port + "/users").toString(), request, UserDTO.class);

        return (UserDTO) response.getBody();
    }

}
