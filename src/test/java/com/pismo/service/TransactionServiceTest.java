package com.pismo.service;

import com.pismo.dto.CreateTransactionDTO;
import com.pismo.dto.TransactionDTO;
import com.pismo.exception.TransactionException;
import com.pismo.mapper.TransactionMapper;
import com.pismo.model.Account;
import com.pismo.model.OperationType;
import com.pismo.model.Transaction;
import com.pismo.repository.AccountRepository;
import com.pismo.repository.OperationTypeRepository;
import com.pismo.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Test
    public void accountServiceShouldBeNotNull() {
        assertNotNull(transactionService);
    }

    @Test
    public void createTransactionWithNullAccountIdShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(null);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Invalid account!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithNullOperationTypeIdShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(null);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Invalid operation type!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithOperationTypeIdLessThanZeroShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(0L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        TransactionException transactionException = assertThrows(TransactionException.class,
                () -> transactionService.create(createTransactionDTO));
        assertEquals("Invalid operation type!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithOperationTypeIdGreaterThanFourShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(5L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Invalid operation type!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithNullAmountShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(null);

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Amount must be greater than zero!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithAmountEqualZeroShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(BigDecimal.ZERO);

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Amount must be greater than zero!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithAmountLessThanZeroShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(-1).setScale(2));

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Amount must be greater than zero!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithInvalidAccountIdShouldThrowError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(0L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        when(accountRepository.findById(0L)).thenReturn(Optional.empty());

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Invalid account!", transactionException.getMessage());
    }

    @Test
    public void createTransactionWithSuccess() {
        Instant now = Instant.now();
        String date = DATE_TIME_FORMATTER.format(now);

        Account foundedAccount = new Account();
        foundedAccount.setId(1L);
        foundedAccount.setDocumentNumber(12345678900L);
        foundedAccount.setBalance(BigDecimal.valueOf(500).setScale(2));

        OperationType operationType = new OperationType(3L, "SAQUE", true);

        Account account = new Account();
        account.setId(1L);
        account.setDocumentNumber(12345678900L);
        account.setBalance(BigDecimal.valueOf(500).setScale(2));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        transaction.setEventDate(now);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(1L);
        savedTransaction.setAccount(account);
        savedTransaction.setOperationType(operationType);
        savedTransaction.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        savedTransaction.setEventDate(now);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(1L);
        transactionDTO.setAccountId(1L);
        transactionDTO.setOperationTypeId(3L);
        transactionDTO.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        transactionDTO.setEventDate(date);

        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(foundedAccount));
        when(operationTypeRepository.findById(3L)).thenReturn(Optional.of(operationType));
        when(transactionMapper.toEntity(foundedAccount, operationType, BigDecimal.valueOf(-123.45).setScale(2))).thenReturn(transaction);
        when(accountRepository.save(foundedAccount)).thenReturn(account);
        when(transactionRepository.save(transaction)).thenReturn(savedTransaction);
        when(transactionMapper.toDTO(savedTransaction)).thenReturn(transactionDTO);

        TransactionDTO response = transactionService.create(createTransactionDTO);

        assertEquals(response.getTransactionId(), savedTransaction.getId());
        assertEquals(response.getAccountId(), savedTransaction.getAccount().getId());
        assertEquals(response.getOperationTypeId(), savedTransaction.getOperationType().getId());
        assertEquals(response.getAmount(), savedTransaction.getAmount());
        assertEquals(response.getEventDate(), DATE_TIME_FORMATTER.format(savedTransaction.getEventDate()));
    }

    @Test
    public void createTransactionWhenBalanceIsNotEnough() {
        Instant now = Instant.now();
        String date = DATE_TIME_FORMATTER.format(now);

        Account foundedAccount = new Account();
        foundedAccount.setId(1L);
        foundedAccount.setDocumentNumber(12345678900L);
        foundedAccount.setBalance(BigDecimal.ZERO);
        foundedAccount.setBalance(BigDecimal.valueOf(100).setScale(2));

        OperationType operationType = new OperationType(3L, "SAQUE", true);

        Account account = new Account();
        account.setId(1L);
        account.setDocumentNumber(12345678900L);
        account.setBalance(BigDecimal.ZERO);
        account.setBalance(BigDecimal.valueOf(100).setScale(2));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        transaction.setEventDate(now);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(1L);
        savedTransaction.setAccount(account);
        savedTransaction.setOperationType(operationType);
        savedTransaction.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        savedTransaction.setEventDate(now);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(1L);
        transactionDTO.setAccountId(1L);
        transactionDTO.setOperationTypeId(3L);
        transactionDTO.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        transactionDTO.setEventDate(date);

        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(foundedAccount));
        when(operationTypeRepository.findById(3L)).thenReturn(Optional.of(operationType));

        verify(transactionMapper, never()).toEntity(foundedAccount, operationType, BigDecimal.valueOf(-123.45).setScale(2));
        verify(accountRepository, never()).save(foundedAccount);
        verify(transactionRepository, never()).save(transaction);
        verify(transactionMapper, never()).toDTO(savedTransaction);

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertEquals("Balance not enough for transaction amount!", transactionException.getMessage());

    }

    @Test
    public void createTransactionWithError() {
        Instant now = Instant.now();
        String date = DATE_TIME_FORMATTER.format(now);

        Account foundedAccount = new Account();
        foundedAccount.setId(1L);
        foundedAccount.setDocumentNumber(12345678900L);
        foundedAccount.setBalance(BigDecimal.valueOf(500).setScale(2));

        OperationType operationType = new OperationType(3L, "SAQUE", true);

        Account account = new Account();
        account.setId(1L);
        account.setDocumentNumber(12345678900L);
        account.setBalance(BigDecimal.valueOf(500).setScale(2));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        transaction.setEventDate(now);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(1L);
        savedTransaction.setAccount(account);
        savedTransaction.setOperationType(operationType);
        savedTransaction.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        savedTransaction.setEventDate(now);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(1L);
        transactionDTO.setAccountId(1L);
        transactionDTO.setOperationTypeId(3L);
        transactionDTO.setAmount(BigDecimal.valueOf(-123.45).setScale(2));
        transactionDTO.setEventDate(date);

        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(1L);
        createTransactionDTO.setOperationTypeId(3L);
        createTransactionDTO.setAmount(BigDecimal.valueOf(123.45).setScale(2));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(foundedAccount));
        when(operationTypeRepository.findById(3L)).thenReturn(Optional.of(operationType));
        when(transactionMapper.toEntity(foundedAccount, operationType, BigDecimal.valueOf(-123.45).setScale(2))).thenReturn(transaction);
        when(accountRepository.save(foundedAccount)).thenThrow(TransactionException.class);

        verify(transactionRepository, never()).save(transaction);
        verify(transactionMapper, never()).toDTO(savedTransaction);

        TransactionException transactionException = assertThrows(TransactionException.class,
                ()-> transactionService.create(createTransactionDTO));
        assertNull(transactionException.getMessage());

    }

}
