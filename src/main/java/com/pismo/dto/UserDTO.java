package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Modelo de retorno de dados de usuário")
public class UserDTO {

    @JsonProperty("user_id")
    @ApiModelProperty(notes = "Identificador do usuário", example = "1")
    private Long id;

    @JsonProperty("email")
    @ApiModelProperty(notes = "Email do usuário", example = "user01@email.com")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
