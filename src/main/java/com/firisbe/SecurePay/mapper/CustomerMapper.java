package com.firisbe.SecurePay.mapper;

import com.firisbe.SecurePay.dto.CustomerDto;
import com.firisbe.SecurePay.entity.Customer;
import com.firisbe.SecurePay.model.request.CreateCustomerRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {

    public Customer toEntity(CreateCustomerRequest request) {
        return Customer.builder()
                .id(null)
                .name(request.getCustomerName())
                .email(request.getEmail())
                .build();
    }

    public CustomerDto entityToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .creditCards(customer.getCreditCards())
                .payments(customer.getPayments())
                .build();
    }

    public List<CustomerDto> entityListToDtoList(List<Customer> customers) {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customers.forEach(customer -> {
            CustomerDto customerDto = CustomerDto.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .creditCards(customer.getCreditCards())
                    .payments(customer.getPayments())
                    .build();
            customerDtoList.add(customerDto);
        });
        return customerDtoList;
    }
}
