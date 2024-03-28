package com.firisbe.SecurePay.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericSuccessResponse {

    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private Object data;
}
