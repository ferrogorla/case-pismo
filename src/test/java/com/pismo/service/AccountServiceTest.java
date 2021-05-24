package com.pismo.service;

import com.pismo.dto.AccountDTO;
import com.pismo.dto.CreateAccountDTO;
import com.pismo.exception.AccountException;
import com.pismo.mapper.AccountMapper;
import com.pismo.model.Account;
import com.pismo.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Test
    public void accountServiceShouldBeNotNull() {
        assertNotNull(accountService);
    }

    @Test
    public void createAccountWithNullDocumentIdShouldThrowError() {
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setDocumentNumber(null);

        AccountException accountException = assertThrows(AccountException.class,
                ()-> accountService.create(createAccountDTO));
        assertEquals("Invalid document number!", accountException.getMessage());
    }

    @Test
    public void createAccountWithSuccess() {
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setDocumentNumber(12345678900L);

        Account account = new Account();
        account.setDocumentNumber(12345678900L);
        account.setBalance(BigDecimal.ZERO);

        Account savedAccount = new Account();
        savedAccount.setId(1L);
        savedAccount.setDocumentNumber(12345678900L);
        savedAccount.setBalance(BigDecimal.ZERO);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setDocumentNumber(12345678900L);
        accountDTO.setBalance(BigDecimal.ZERO);

        when(accountRepository.findByDocumentNumber(12345678900L)).thenReturn(Optional.empty());
        when(accountMapper.toEntity(createAccountDTO)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(savedAccount);
        when(accountMapper.toDTO(savedAccount)).thenReturn(accountDTO);

        AccountDTO response = accountService.create(createAccountDTO);

        assertEquals(savedAccount.getId(), response.getId());
        assertEquals(savedAccount.getDocumentNumber(), response.getDocumentNumber());
        assertEquals(savedAccount.getBalance(), response.getBalance());
    }

    @Test
    public void createAccountWithDuplicatedDocumentIdShouldThrowError() {
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setDocumentNumber(12345678900L);

        Account account = new Account();
        account.setId(1L);
        account.setDocumentNumber(12345678900L);
        account.setBalance(BigDecimal.ZERO);

        when(accountRepository.findByDocumentNumber(12345678900L)).thenReturn(Optional.of(account));

        AccountException accountException = assertThrows(AccountException.class,
                ()-> accountService.create(createAccountDTO));
        assertEquals("Account 12345678900 already exists!", accountException.getMessage());
    }

    @Test
    public void findAccountWithSuccess() {
        Account account = new Account();
        account.setId(1L);
        account.setDocumentNumber(12345678900L);
        account.setBalance(BigDecimal.ZERO);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setDocumentNumber(12345678900L);
        accountDTO.setBalance(BigDecimal.ZERO);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountMapper.toDTO(account)).thenReturn(accountDTO);

        AccountDTO foundedAccountDTO = accountService.getById(1L);

        assertNotNull(foundedAccountDTO);
        assertEquals(account.getId(), foundedAccountDTO.getId());
        assertEquals(account.getDocumentNumber(), foundedAccountDTO.getDocumentNumber());
        assertEquals(account.getBalance(), foundedAccountDTO.getBalance());
    }

    @Test
    public void findAccountWithNotExistingIdShouldThrowError() {
        AccountException accountException = assertThrows(AccountException.class,
                ()-> accountService.getById(0L));
        assertEquals("Account 0 not found!", accountException.getMessage());
    }

}
