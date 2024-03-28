package com.firisbe.SecurePay.service;

import com.firisbe.SecurePay.dto.CustomerDto;
import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.entity.Customer;
import com.firisbe.SecurePay.exception.AlreadyExistException;
import com.firisbe.SecurePay.exception.ResourceNotFoundException;
import com.firisbe.SecurePay.mapper.CreditCardMapper;
import com.firisbe.SecurePay.mapper.CustomerMapper;
import com.firisbe.SecurePay.model.request.CreateCustomerRequest;
import com.firisbe.SecurePay.model.request.UpdateCreditCardInfoRequest;
import com.firisbe.SecurePay.model.response.GenericSuccessResponse;
import com.firisbe.SecurePay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final CreditCardMapper creditCardMapper;

    // TODO: If the number of customers is large, caching or pagination structures should be used.
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.entityListToDtoList(customers);
    }

    protected List<Customer> getCustomersByName(String name) {
        return customerRepository.findByName(name);
    }

    public CustomerDto addCustomer(CreateCustomerRequest request) {
        if (isEmailAlreadyExist(request.getEmail())) {
            log.warn("The email is being used by another user! Please enter a different email address.");
            throw new AlreadyExistException("The email is being used by another user!");
        }
        Customer savedCustomer = customerRepository.save(customerMapper.toEntity(request));

        return customerMapper.entityToDto(savedCustomer);
    }

    private boolean isEmailAlreadyExist(String email) {
        List<CustomerDto> customers = getAllCustomers();
        return customers.stream().anyMatch(customerDto -> customerDto.getEmail().equals(email));
    }

    public CustomerDto updateCreditCardInfoByCustomerId(UpdateCreditCardInfoRequest request) {
        Customer customer = findEntityByCustomerId(request.getCustomerId());
        CreditCard creditCard = creditCardMapper.toEntity(request);
        if (isCardNumberAlreadyExist(customer.getCreditCards(), request.getCardNumber())) {
            log.error("Given card number: {} is already exist", request.getCardNumber());
            throw new AlreadyExistException("Given card number is already exist");
        }
        customer.getCreditCards().add(creditCard);
        creditCard.setCustomer(customer);
        Customer updatedCustomerWithCreditCardInfo = customerRepository.save(customer);
        return customerMapper.entityToDto(updatedCustomerWithCreditCardInfo);
    }

    private Boolean isCardNumberAlreadyExist(Set<CreditCard> creditCards, String cardNumber) {
        return creditCards.stream().anyMatch(creditCard -> creditCard.getEncryptedCardNumber().equals(cardNumber));
    }

    public CustomerDto findByCustomerId(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find any customer with given id: " + id));

        return customerMapper.entityToDto(customer);
    }

    protected Customer findEntityByCustomerId(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find any customer with given id: " + id));
    }
}
