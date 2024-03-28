package com.firisbe.SecurePay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.firisbe.SecurePay.dto.CustomerDto;
import com.firisbe.SecurePay.model.request.CreateCustomerRequest;
import com.firisbe.SecurePay.model.request.UpdateCreditCardInfoRequest;
import com.firisbe.SecurePay.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDto> customers = Collections.singletonList(new CustomerDto(1L, "arda@gmail.com",
                "arda", null, null));
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void addCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto(1L, "arda@gmail.com",
                "arda", null, null);
        CreateCustomerRequest request = new CreateCustomerRequest("arda", "arda@gmail.com");
        when(customerService.addCustomer(request)).thenReturn(customerDto);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void updateCreditCardInfoByCustomerId() throws Exception {
        CustomerDto customerDto = new CustomerDto(1L, "arda@gmail.com",
                "arda", null, null);
        UpdateCreditCardInfoRequest request = new UpdateCreditCardInfoRequest(1L, "123123", 555L, LocalDate.of(2024, 6, 1));
        when(customerService.updateCreditCardInfoByCustomerId(request)).thenReturn(customerDto);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(put("/api/v1/customers/updateCreditCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists());
    }
}