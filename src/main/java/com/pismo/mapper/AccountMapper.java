package com.pismo.mapper;

import com.pismo.dto.AccountDTO;
import com.pismo.dto.CreateAccountDTO;
import com.pismo.model.Account;
import com.pismo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountMapper {

    @Autowired
    private TransactionMapper transactionMapper;

    public AccountDTO toDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setDocumentNumber(account.getDocumentNumber());
        accountDTO.setBalance(account.getBalance());
        return accountDTO;
    }

    public AccountDTO toDTO(Account account, List<Transaction> transactions) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setDocumentNumber(account.getDocumentNumber());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setTransactions(transactionMapper.toListDTO(transactions));
        return accountDTO;
    }

    public List<AccountDTO> toListDTO(List<Account> accounts) {
        List<AccountDTO> results = new ArrayList<>();
        for(Account account : accounts) {
            results.add(toDTO(account));
        }
        return results;
    }

    public Account toEntity(CreateAccountDTO createAccountDTO) {
        Account account = new Account();
        account.setDocumentNumber(createAccountDTO.getDocumentNumber());
        account.setBalance(0d);
        return account;
    }

}
