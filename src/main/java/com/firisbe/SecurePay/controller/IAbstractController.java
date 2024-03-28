package com.firisbe.SecurePay.controller;

import com.firisbe.SecurePay.model.response.GenericSuccessResponse;
import org.springframework.http.ResponseEntity;

public interface IAbstractController {

    default ResponseEntity<?> buildSuccessResponseEntity(GenericSuccessResponse response) {
        return new ResponseEntity<>(response, response.getStatus());
    }

}
