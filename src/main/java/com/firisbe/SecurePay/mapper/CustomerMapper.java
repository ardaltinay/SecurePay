package com.firisbe.SecurePay.mapper;

import com.firisbe.SecurePay.dto.CustomerDto;
import com.firisbe.SecurePay.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDto dto) {
        return new Customer(dto.id(), dto.email(), dto.creditCards(), dto.payments());
    }

    public CustomerDto toDto(Customer entity) {
        return new CustomerDto(entity.getId(), entity.getEmail(), entity.getCreditCards(), entity.getPayments());
    }
}
