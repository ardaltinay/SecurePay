package com.firisbe.SecurePay.service;

import com.firisbe.SecurePay.dto.PaymentDto;
import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.entity.Customer;
import com.firisbe.SecurePay.entity.Payment;
import com.firisbe.SecurePay.exception.CustomerCreditCardDidNotMatchException;
import com.firisbe.SecurePay.mapper.PaymentMapper;
import com.firisbe.SecurePay.model.request.CreatePaymentRequest;
import com.firisbe.SecurePay.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository repository;

    private final PaymentMapper mapper;

    private final CustomerService customerService;

    private final  CreditCardService creditCardService;


    public PaymentDto addPayment(CreatePaymentRequest request) {
        Customer customer = customerService.findEntityByCustomerId(request.getCustomerId());
        Payment payment = mapper.toEntity(request);
        CreditCard creditCard = customer.getCreditCards().stream()
                .filter(card -> request.getCreditCardId().equals(card.getId())).findAny().orElseThrow(() -> {
                    log.error("The Customer's credit card did not match the given credit card information!");
                    return new CustomerCreditCardDidNotMatchException("The Customer's credit card did not match the given credit card information! Please try another credit card.");
                });
        creditCard.getPayments().add(payment);
        payment.setCreditCard(creditCard);
        payment.setCustomer(customer);
        Payment savedPayment = repository.save(payment);

        return mapper.entityToDto(savedPayment);
    }

    public List<PaymentDto> searchPaymentsByQueryParams(Long customerNumber, String cardNumber, String customerName) {
        if (customerNumber != null) {
            Customer customer = customerService.findEntityByCustomerId(customerNumber);
            return mapper.entityListToDtoList(customer.getPayments().stream().toList());
        } else if (cardNumber != null) {
            List<CreditCard> creditCards = creditCardService.getCreditCardsByCardNumber(cardNumber);
            List<Payment> payments = new ArrayList<>();
            creditCards.forEach(creditCard -> {
                payments.addAll(creditCard.getPayments());
            });
            return mapper.entityListToDtoList(payments);
        } else if (customerName != null) {
            List<Customer> customers = customerService.getCustomersByName(customerName);
            List<Payment> payments = new ArrayList<>();
            customers.forEach(customer -> {
                payments.addAll(customer.getPayments());
            });
            return mapper.entityListToDtoList(payments);
        } else {
            throw new IllegalArgumentException("At least one search criteria must be provided! Search criteria: customerNumber, cardNumber or customerName");
        }
    }

    public List<PaymentDto> listPaymentsByDateInterval(LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Payment> payments = repository.findPaymentsByDateInterval(startDate, endDate, pageable);
        return mapper.entityListToDtoList(payments);
    }

}
