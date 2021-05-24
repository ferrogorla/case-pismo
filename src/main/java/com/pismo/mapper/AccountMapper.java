package com.pismo.mapper;

import com.pismo.dto.AccountDTO;
import com.pismo.dto.CreateAccountDTO;
import com.pismo.model.Account;
import com.pismo.model.Transaction;
import com.pismo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        accountDTO.setUserId(account.getUser().getId());
        return accountDTO;
    }

    public AccountDTO toDTO(Account account, List<Transaction> transactions) {
        AccountDTO accountDTO = toDTO(account);
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
        User user = new User();
        user.setId(createAccountDTO.getUserId());
        Account account = new Account();
        account.setUser(user);
        account.setDocumentNumber(createAccountDTO.getDocumentNumber());
        account.setBalance(BigDecimal.valueOf(500).setScale(2));
        return account;
    }

}
