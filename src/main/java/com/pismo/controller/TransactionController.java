package com.pismo.controller;

import com.pismo.dto.CreateTransactionDTO;
import com.pismo.dto.TransactionDTO;
import com.pismo.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@Api(tags = "Operações de transação")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @ApiOperation(value = "Listar todas as transações")
    public ResponseEntity<TransactionDTO> create(@RequestBody CreateTransactionDTO createTransactionDTO) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.create(createTransactionDTO));
    }

}