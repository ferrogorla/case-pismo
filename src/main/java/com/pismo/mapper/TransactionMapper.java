package com.pismo.mapper;

import com.pismo.dto.TransactionDTO;
import com.pismo.model.Account;
import com.pismo.model.OperationType;
import com.pismo.model.Transaction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TransactionMapper {

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getId());
        transactionDTO.setAccountId(transaction.getAccount().getId());
        transactionDTO.setOperationTypeId(transaction.getOperationType().getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setEventDate(DATE_TIME_FORMATTER.format(transaction.getEventDate()));
        return transactionDTO;
    }

    public List<TransactionDTO> toListDTO(List<Transaction> transactions) {
        List<TransactionDTO> results = new ArrayList<>();
        for(Transaction transaction : transactions) {
            results.add(toDTO(transaction));
        }
        return results;
    }

    public Transaction toEntity(Account account, OperationType operationType, Double amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setAmount(amount);
        transaction.setEventDate(Instant.now());
        return transaction;
    }

}
