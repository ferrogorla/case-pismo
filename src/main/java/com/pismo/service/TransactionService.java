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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Transactional
    public TransactionDTO create(CreateTransactionDTO createTransactionDTO) {

        validateInputs(createTransactionDTO);

        var account = accountRepository.findById(createTransactionDTO.getAccountId())
                .orElseThrow(() -> new TransactionException("Invalid account!"));

        var operationType = operationTypeRepository.findById(createTransactionDTO.getOperationTypeId())
                .orElseThrow(() -> new TransactionException("Invalid operation type!"));

        checkBalance(operationType, account.getBalance(), createTransactionDTO.getAmount());

        var amount = createTransactionDTO.getAmount().multiply(operationType.getOperationSign()).setScale(2);

        var transaction = transactionMapper.toEntity(account, operationType, amount);

        calculateNewBalanceForAccount(account, transaction);

        var savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toDTO(savedTransaction);
    }

    public List<TransactionDTO> getAllByAccountId(Long accountId, Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        var transactions = transactionRepository.findByAccountId(accountId, paging);
        return transactionMapper.toListDTO(transactions);
    }

    private void validateInputs(CreateTransactionDTO createTransactionDTO) {
        if (createTransactionDTO.getAccountId() == null)
            throw new TransactionException("Invalid account!");

        if (createTransactionDTO.getOperationTypeId() == null ||
                createTransactionDTO.getOperationTypeId() <= 0 ||
                createTransactionDTO.getOperationTypeId() > 4)
            throw new TransactionException("Invalid operation type!");

        if (createTransactionDTO.getAmount() == null ||
                createTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new TransactionException("Amount must be greater than zero!");
    }

    private void calculateNewBalanceForAccount(Account account, Transaction transaction) {
        var newBalance = account.getBalance().add(transaction.getAmount()).setScale(2);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private void checkBalance(OperationType operationType, BigDecimal balance, BigDecimal amount) {
        if(!operationType.isPayment()) {
            var newLimit = balance.subtract(amount);
            if (newLimit.compareTo(BigDecimal.ZERO) < 0)
                throw new TransactionException("Balance not enough for transaction amount!");
        }
    }

}
