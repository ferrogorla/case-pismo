package com.pismo.controller;

import com.pismo.dto.AccountDTO;
import com.pismo.dto.CreateAccountDTO;
import com.pismo.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Api(tags = "Operações da conta")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @ApiOperation(value = "Listar todas as contas")
    public ResponseEntity<List<AccountDTO>> getAll() {
        return ResponseEntity
                .ok(accountService.getAll());
    }

    @GetMapping("/{accountId}")
    @ApiOperation(value = "Listar conta pelo id")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long accountId) {
        return ResponseEntity
                .ok(accountService.getById(accountId));
    }

    @GetMapping("/{accountId}/transactions")
    @ApiOperation(value = "Listar conta e transações pelo id da conta")
    public ResponseEntity<AccountDTO> getAccountAndTransactions(@PathVariable Long accountId) {
        return ResponseEntity
                .ok(accountService.getAccountAndTransactions(accountId));
    }

    @PostMapping
    @ApiOperation(value = "Criar conta")
    public ResponseEntity<AccountDTO> create(@RequestBody CreateAccountDTO createAccountDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.create(createAccountDTO));
    }

}
