package com.firisbe.SecurePay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.firisbe.SecurePay.dto.PaymentDto;
import com.firisbe.SecurePay.model.request.CreatePaymentRequest;
import com.firisbe.SecurePay.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Test
    void addPayment() throws Exception {
        PaymentDto paymentDto = new PaymentDto(1L, new BigDecimal(500),
                LocalDate.of(2024, 6, 1), null, null,
                null, null, null);
        CreatePaymentRequest request = new CreatePaymentRequest(1L, 1L, new BigDecimal(500));
        when(paymentService.addPayment(request)).thenReturn(paymentDto);

        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void searchPayments() throws Exception {
        PaymentDto paymentDto = new PaymentDto(1L, new BigDecimal(500),
                LocalDate.of(2024, 6, 1), null, null,
                null, null, null);
        List<PaymentDto> paymentDtoList = Collections.singletonList(paymentDto);
        when(paymentService.searchPaymentsByQueryParams(any(), any(), any())).thenReturn(paymentDtoList);

        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        mockMvc.perform(get("/api/v1/payments/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void listPaymentsByDateInterval() throws Exception {
        PaymentDto paymentDto = new PaymentDto(1L, new BigDecimal(500),
                LocalDate.of(2024, 6, 1), null, null,
                null, null, null);
        List<PaymentDto> paymentDtoList = Collections.singletonList(paymentDto);
        when(paymentService.listPaymentsByDateInterval(any(), any(), anyInt(), anyInt())).thenReturn(paymentDtoList);

        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        mockMvc.perform(get("/api/v1/payments/list")
                        .param("startDate", "2024-03-01")
                        .param("endDate", "2024-03-31")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray());
    }
}