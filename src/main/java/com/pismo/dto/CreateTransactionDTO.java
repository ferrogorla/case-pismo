package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Modelo para criação de transação")
public class CreateTransactionDTO {

    @JsonProperty("account_id")
    @ApiModelProperty(notes = "Identificador da conta", example = "1")
    private Long accountId;

    @JsonProperty("operation_type_id")
    @ApiModelProperty(notes = "Identificador da operação", example = "3")
    private Long operationTypeId;

    @JsonProperty("amount")
    @ApiModelProperty(notes = "Valor da transação", example = "123.45")
    private Double amount;

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

}
