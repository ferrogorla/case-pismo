package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Modelo para criação de conta")
public class CreateAccountDTO {

    @JsonProperty("document_number")
    @ApiModelProperty(notes = "Número do documento", example = "12345678900")
    private Long documentNumber;

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

}
