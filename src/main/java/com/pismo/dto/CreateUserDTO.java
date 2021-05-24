package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Modelo para criação de usuário")
public class CreateUserDTO {

    @JsonProperty("email")
    @ApiModelProperty(notes = "Email do usuário", example = "user01@email.com")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
