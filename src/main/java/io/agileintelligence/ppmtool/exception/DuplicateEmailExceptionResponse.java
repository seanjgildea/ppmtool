package io.agileintelligence.ppmtool.exception;

import lombok.Data;

@Data
public class DuplicateEmailExceptionResponse {
    private String username;
    public DuplicateEmailExceptionResponse(String username) {  this.username = username;    }
}
