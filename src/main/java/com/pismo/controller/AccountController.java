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
    @ApiOperation(value = "Listar todas as contas de um usuário")
    public ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam Long userId,
                                                        @RequestParam(defaultValue = "0") Integer pageNumber,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity
                .ok(accountService.getAccounts(userId, pageNumber, pageSize));
    }

    @GetMapping("/{accountId}")
    @ApiOperation(value = "Listar conta pelo id")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long accountId) {
        return ResponseEntity
                .ok(accountService.getById(accountId));
    }

    @GetMapping("/{accountId}/transactions")
    @ApiOperation(value = "Listar conta com transações pelo id")
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
