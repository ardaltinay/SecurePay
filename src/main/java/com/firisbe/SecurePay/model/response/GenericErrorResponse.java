package com.firisbe.SecurePay.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GenericErrorResponse {
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private LocalDateTime timestamp;

    public GenericErrorResponse(HttpStatus status, String message, LocalDateTime time) {
        this.status = status;
        this.message = message;
        this.timestamp = time;
    }

    public GenericErrorResponse(HttpStatus status, List<String> errors, LocalDateTime time) {
        this.status = status;
        this.errors = errors;
        this.timestamp = time;
    }
}
