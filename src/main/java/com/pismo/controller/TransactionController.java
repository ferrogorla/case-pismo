package com.pismo.controller;

import com.pismo.dto.CreateTransactionDTO;
import com.pismo.dto.TransactionDTO;
import com.pismo.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Api(tags = "Operações de transação")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @ApiOperation(value = "Criar transação")
    public ResponseEntity<TransactionDTO> create(@RequestBody CreateTransactionDTO createTransactionDTO) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.create(createTransactionDTO));
    }

    @GetMapping
    @ApiOperation(value = "Listar todas as transações de uma conta")
    public ResponseEntity<List<TransactionDTO>> getAllByAccountId(@RequestParam Long accountId,
                                                                  @RequestParam(defaultValue = "0") Integer pageNumber,
                                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(transactionService.getAllByAccountId(accountId, pageNumber, pageSize));
    }

}