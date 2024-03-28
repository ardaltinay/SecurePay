package com.firisbe.SecurePay.mapper;

import com.firisbe.SecurePay.dto.CustomerDto;
import com.firisbe.SecurePay.entity.Customer;
import com.firisbe.SecurePay.model.request.CreateCustomerRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

    @InjectMocks
    private CustomerMapper customerMapper;

    @Mock
    private CreateCustomerRequest createCustomerRequest;

    @Test
    void toEntity() {
        // given
        when(createCustomerRequest.getCustomerName()).thenReturn("Arda");
        when(createCustomerRequest.getEmail()).thenReturn("arda@gmail.com");

        // when
        Customer customer = customerMapper.toEntity(createCustomerRequest);

        // then
        assertEquals("Arda", customer.getName());
        assertEquals("arda@gmail.com", customer.getEmail());
    }

    @Test
    void entityToDto() {
        // given
        Customer customer = Customer.builder()
                .id(1L)
                .name("Arda")
                .email("arda@gmail.com")
                .build();

        // when
        CustomerDto customerDto = customerMapper.entityToDto(customer);

        // then
        assertEquals(1L, customerDto.getId());
        assertEquals("Arda", customerDto.getName());
        assertEquals("arda@gmail.com", customerDto.getEmail());
    }

    @Test
    void entityListToDtoList() {
        // given
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Arda")
                .email("arda@gmail.com")
                .build();
        Customer customer2 = Customer.builder()
                .id(2L)
                .name("Berkay")
                .email("berkay@gmail.com")
                .build();
        List<Customer> customers = Arrays.asList(customer1, customer2);

        // when
        List<CustomerDto> customerDtoList = customerMapper.entityListToDtoList(customers);

        // then
        assertEquals(2, customerDtoList.size());
        assertEquals(1L, customerDtoList.get(0).getId());
        assertEquals("Arda", customerDtoList.get(0).getName());
        assertEquals("arda@gmail.com", customerDtoList.get(0).getEmail());
        assertEquals(2L, customerDtoList.get(1).getId());
        assertEquals("Berkay", customerDtoList.get(1).getName());
        assertEquals("berkay@gmail.com", customerDtoList.get(1).getEmail());
    }
}