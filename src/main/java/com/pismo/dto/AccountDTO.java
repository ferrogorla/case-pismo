package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Modelo de retorno de dados da conta")
public class AccountDTO {

    @JsonProperty("account_id")
    @ApiModelProperty(notes = "Identificador da conta", example = "1")
    private Long id;

    @JsonProperty("document_number")
    @ApiModelProperty(notes = "Número do documento", example = "12345678900")
    private Long documentNumber;

    @JsonProperty("balance")
    @ApiModelProperty(notes = "Saldo da conta", example = "123.45")
    private Double balance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("transactions")
    @ApiModelProperty(notes = "Lista de transações referente a conta")
    private List<TransactionDTO> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

}
