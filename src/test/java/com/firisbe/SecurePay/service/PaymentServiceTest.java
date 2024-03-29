package com.firisbe.SecurePay.service;

import com.firisbe.SecurePay.dto.PaymentDto;
import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.entity.Customer;
import com.firisbe.SecurePay.entity.Payment;
import com.firisbe.SecurePay.exception.CustomerCreditCardDidNotMatchException;
import com.firisbe.SecurePay.mapper.PaymentMapper;
import com.firisbe.SecurePay.model.request.CreatePaymentRequest;
import com.firisbe.SecurePay.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private CustomerService customerService;

    @Mock
    private CreditCardService creditCardService;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPayment() {
        // given
        CreatePaymentRequest request = new CreatePaymentRequest(1L, 1L,
                new BigDecimal(500));
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                new HashSet<>(), new HashSet<>());
        CreditCard creditCard = new CreditCard(1L, "123412341234", 555L,
                LocalDate.MAX, customer, new HashSet<>());
        Payment payment = new Payment(1L, new BigDecimal(500), LocalDate.MAX, customer, creditCard);
        PaymentDto paymentDto = new PaymentDto(1L, new BigDecimal(500), LocalDate.MAX,
                customer.getName(), customer.getEmail(),
                creditCard.getEncryptedCardNumber(), creditCard.getCvvNumber(),
                creditCard.getExpireDate());
        customer.setCreditCards(Set.of(creditCard));

        when(customerService.findEntityByCustomerId(request.getCustomerId())).thenReturn(customer);
        when(paymentMapper.toEntity(request)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.entityToDto(payment)).thenReturn(paymentDto);

        // when
        PaymentDto result = paymentService.addPayment(request);

        // then
        assertNotNull(result);
        assertEquals(paymentDto.getId(), result.getId());

        verify(customerService).findEntityByCustomerId(request.getCustomerId());
        verify(paymentMapper).toEntity(request);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).entityToDto(payment);
    }

    @Test
    void addPayment_WhenCreditCardDidNotMatch_ShouldThrowCustomerCreditCardDidNotMatchException() {
        // given
        CreatePaymentRequest request = new CreatePaymentRequest(1L, 1L,
                new BigDecimal(500));
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                new HashSet<>(), new HashSet<>());
        CreditCard creditCard = new CreditCard(2L, "123412341234", 555L,
                LocalDate.MAX, customer, new HashSet<>());
        Payment payment = new Payment(1L, new BigDecimal(500), LocalDate.MAX, customer, creditCard);
        customer.setCreditCards(Set.of(creditCard));

        when(customerService.findEntityByCustomerId(request.getCustomerId())).thenReturn(customer);
        when(paymentMapper.toEntity(request)).thenReturn(payment);

        // then
        assertThrows(CustomerCreditCardDidNotMatchException.class, () -> paymentService.addPayment(request));
    }

    @Test
    void searchPaymentsByQueryParams_WithCustomerNumber() {
        // given
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                new HashSet<>(), new HashSet<>());

        PaymentDto expectedDto = new PaymentDto(1L, new BigDecimal(500), LocalDate.MAX,
                customer.getName(), customer.getEmail(), null,
                null, null);

        when(customerService.findEntityByCustomerId(1L)).thenReturn(customer);
        when(paymentMapper.entityListToDtoList(customer.getPayments().stream().toList())).thenReturn(List.of(expectedDto));

        // when
        List<PaymentDto> result = paymentService.searchPaymentsByQueryParams(1L, null, null);
        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    void searchPaymentsByQueryParams_WithCardNumber() {
        // given
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                new HashSet<>(), new HashSet<>());
        CreditCard creditCard = new CreditCard(1L, "123412341234", 555L, LocalDate.MAX, customer, new HashSet<>());

        PaymentDto expectedDto = new PaymentDto(1L, new BigDecimal(500), LocalDate.MAX,
                customer.getName(), customer.getEmail(), creditCard.getEncryptedCardNumber(),
                creditCard.getCvvNumber(), creditCard.getExpireDate());

        when(customerService.findEntityByCustomerId(1L)).thenReturn(customer);
        when(paymentMapper.entityListToDtoList(customer.getPayments().stream().toList())).thenReturn(List.of(expectedDto));

        // when
        List<PaymentDto> result = paymentService.searchPaymentsByQueryParams(null, creditCard.getEncryptedCardNumber(), null);
        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    void searchPaymentsByQueryParams_WithCustomerName() {
        // given
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                new HashSet<>(), new HashSet<>());

        PaymentDto expectedDto = new PaymentDto(1L, new BigDecimal(500), LocalDate.MAX,
                customer.getName(), customer.getEmail(), null,
                null, null);

        when(customerService.findEntityByCustomerId(1L)).thenReturn(customer);
        when(paymentMapper.entityListToDtoList(customer.getPayments().stream().toList())).thenReturn(List.of(expectedDto));

        // when
        List<PaymentDto> result = paymentService.searchPaymentsByQueryParams(null, null, "Arda");
        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    public void searchPaymentsByQueryParams_NoCriteriaProvided() {
        // Call the service method without providing any search criteria
        assertThrows(IllegalArgumentException.class, () -> paymentService.searchPaymentsByQueryParams(null, null, null));
    }

    @Test
    void listPaymentsByDateInterval() {
        // given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 1);
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        Payment payment1 = new Payment(1L, new BigDecimal(500), LocalDate.of(2024, 5,1), null, null);
        Payment payment2 = new Payment(2L, new BigDecimal(600), LocalDate.of(2024, 4,1), null, null);
        List<Payment> paymentList = List.of(payment1, payment2);

        PaymentDto paymentDto1 = new PaymentDto(1L, new BigDecimal(500), LocalDate.of(2024, 5,1),
                null, null, null, null, null);
        PaymentDto paymentDto2 = new PaymentDto(2L, new BigDecimal(600), LocalDate.of(2024, 4,1),
                null, null, null, null, null);
        List<PaymentDto> paymentDtoList = List.of(paymentDto1, paymentDto2);

        when(paymentRepository.findPaymentsByDateInterval(startDate, endDate, pageable)).thenReturn(paymentList);
        when(paymentMapper.entityListToDtoList(paymentList)).thenReturn(paymentDtoList);

        // when
        List<PaymentDto> result = paymentService.listPaymentsByDateInterval(startDate, endDate, page, size);

        // then
        assertNotNull(result);
        assertEquals(paymentDtoList.size(), result.size());
        assertEquals(paymentDtoList, result);

        verify(paymentRepository).findPaymentsByDateInterval(startDate, endDate, pageable);
        verify(paymentMapper).entityListToDtoList(paymentList);
    }
}