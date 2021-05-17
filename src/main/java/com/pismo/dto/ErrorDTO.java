package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDTO {

    @JsonProperty("error")
    private String error;

    public ErrorDTO(String error) {
        this.error = error;
    }

}
