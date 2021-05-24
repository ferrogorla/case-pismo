package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Modelo para criação de conta")
public class CreateAccountDTO {

    @JsonProperty("user_id")
    @ApiModelProperty(notes = "Identificador do usuário", example = "1")
    private Long userId;

    @JsonProperty("document_number")
    @ApiModelProperty(notes = "Número do documento", example = "12345678900")
    private Long documentNumber;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

}
