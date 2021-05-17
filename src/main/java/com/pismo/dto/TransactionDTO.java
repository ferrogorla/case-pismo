package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Modelo de retorno de dados da transação")
public class TransactionDTO {

    @JsonProperty("transaction_id")
    @ApiModelProperty(notes = "Identificador da transação", example = "1")
    private Long transactionId;

    @JsonProperty("account_id")
    @ApiModelProperty(notes = "Identificador da conta", example = "1")
    private Long accountId;

    @JsonProperty("operation_type_id")
    @ApiModelProperty(notes = "Identificador da operação", example = "3")
    private Long operationTypeId;

    @JsonProperty("amount")
    @ApiModelProperty(notes = "Valor da transação", example = "123.45")
    private Double amount;

    @JsonProperty("event_date")
    @ApiModelProperty(notes = "Data da realização da transação")
    private String eventDate;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

}
