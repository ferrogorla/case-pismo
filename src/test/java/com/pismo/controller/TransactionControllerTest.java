package com.pismo.controller;

import com.pismo.dto.CreateTransactionDTO;
import com.pismo.dto.TransactionDTO;
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
public class TransactionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void create() throws Exception {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(123.45D);

        HttpEntity<CreateTransactionDTO> request = new HttpEntity<>(createTransactionDTO);

        ResponseEntity response = restTemplate.postForEntity(new URL("http://localhost:" + port + "/transactions").toString(), request, TransactionDTO.class);

        TransactionDTO transactionDTO = (TransactionDTO) response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(1L, transactionDTO.getAccountId());
        assertEquals(3L, transactionDTO.getOperationTypeId());
        assertEquals(-123.45D, transactionDTO.getAmount());
    }

}
