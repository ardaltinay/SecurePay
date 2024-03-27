package com.firisbe.SecurePay.service;

import com.firisbe.SecurePay.dto.CustomerDto;
import com.firisbe.SecurePay.entity.Customer;
import com.firisbe.SecurePay.mapper.CustomerMapper;
import com.firisbe.SecurePay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.save(customerMapper.toEntity(customerDto));
        return customerMapper.toDto(customer);
    }
}
