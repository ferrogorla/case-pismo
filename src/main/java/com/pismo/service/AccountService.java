package com.pismo.service;

import com.pismo.dto.AccountDTO;
import com.pismo.dto.CreateAccountDTO;
import com.pismo.exception.AccountException;
import com.pismo.mapper.AccountMapper;
import com.pismo.model.Account;
import com.pismo.repository.AccountRepository;
import com.pismo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountMapper accountMapper;

    public List<AccountDTO> getAll(){
        var accounts = accountRepository.findAll();
        return accountMapper.toListDTO(accounts);
    }

    public AccountDTO getById(Long accountId) {
        var account = findById(accountId);
        return accountMapper.toDTO(account);
    }

    public AccountDTO getAccountAndTransactions(Long accountId) {
        var account = findById(accountId);
        var transactions = transactionRepository.findByAccountId(accountId);
        return accountMapper.toDTO(account, transactions);
    }

    public AccountDTO create(CreateAccountDTO createAccountDTO) {
        if (createAccountDTO.getDocumentNumber() == null)
            throw new AccountException("Invalid document number!");

        verifyIfAccountExists(createAccountDTO.getDocumentNumber());

        var account = accountMapper.toEntity(createAccountDTO);

        var savedAccount = accountRepository.save(account);

        return accountMapper.toDTO(savedAccount);
    }

    private Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException("Account " + accountId + " not found!"));
    }

    private void verifyIfAccountExists(Long documentNumber) {
        var accountExists = accountRepository.findByDocumentNumber(documentNumber);
        if (accountExists.isPresent())
            throw new AccountException("Account " + documentNumber + " already exists!");
    }

}
