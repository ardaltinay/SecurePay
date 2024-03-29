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
import com.firisbe.SecurePay.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CreditCardMapper creditCardMapper;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers() {
        // given
        List<Customer> customers = List.of(
                new Customer(1L, "arda@gmail.com", "Arda",
                        Collections.emptySet(), Collections.emptySet()),
                new Customer(2L, "berkay@gmail.com", "Berkay",
                        Collections.emptySet(), Collections.emptySet())
        );
        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.entityListToDtoList(customers)).thenReturn(List.of(
                new CustomerDto(1L, "arda@gmail.com", "Arda",
                        Collections.emptySet(), Collections.emptySet()),
                new CustomerDto(2L, "berkay@gmail.com", "Berkay",
                        Collections.emptySet(), Collections.emptySet())
        ));

        // when
        List<CustomerDto> result = customerService.getAllCustomers();

        // then
        assertEquals(2, result.size());
        assertEquals("Arda", result.get(0).getName());
        assertEquals("arda@gmail.com", result.get(0).getEmail());
        assertEquals("Berkay", result.get(1).getName());
        assertEquals("berkay@gmail.com", result.get(1).getEmail());
    }

    @Test
    void getCustomersByName() {
        // given
        String name = "Arda";
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                Collections.emptySet(), Collections.emptySet());

        when(customerRepository.findByName(name)).thenReturn(List.of(customer));

        // when
        List<Customer> customers = customerService.getCustomersByName(name);

        // then
        assertNotNull(customers);
        assertEquals(customer.getName(), customers.get(0).getName());
    }

    @Test
    void addCustomer() {
        // given
        CreateCustomerRequest request = new CreateCustomerRequest("Arda", "arda@gmail.com");
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                Collections.emptySet(), Collections.emptySet());
        when(customerMapper.toEntity(request)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.entityToDto(customer)).thenReturn(new CustomerDto(1L, "arda@gmail.com",
                "Arda", Collections.emptySet(), Collections.emptySet()));

        // when
        CustomerDto result = customerService.addCustomer(request);

        // then
        assertNotNull(result);
        assertEquals("Arda", result.getName());
        assertEquals("arda@gmail.com", result.getEmail());
    }

    @Test
    void addCustomer_EmailAlreadyExist() {
        // given
        CreateCustomerRequest request = new CreateCustomerRequest("Arda", "arda@gmail.com");
        Customer customer = new Customer(1L, "arda@gmail.com", "Arda",
                Collections.emptySet(), Collections.emptySet());
        CustomerDto customerDto = new CustomerDto(1L, "arda@gmail.com",
                "Arda", Collections.emptySet(), Collections.emptySet());

        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        when(customerMapper.entityListToDtoList(List.of(customer))).thenReturn(List.of(customerDto));

        // then
        assertThrows(AlreadyExistException.class, () -> customerService.addCustomer(request));
    }

    @Test
    void updateCreditCardInfoByCustomerId() {
        // given
        Long customerId = 1L;
        UpdateCreditCardInfoRequest request = new UpdateCreditCardInfoRequest(customerId, "1234567890123456",
                544L, LocalDate.MAX);
        Customer customer = new Customer(customerId, "arda@gmail.com", "Arda",
                new HashSet<>(), Collections.emptySet());
        CreditCard creditCard = new CreditCard(1L, "1234567890123456", 544L,
                LocalDate.MAX, customer, Collections.emptySet());
        CustomerDto expectedDto = new CustomerDto(1L, "arda@gmail.com", "Arda",
                new HashSet<>(), Collections.emptySet());

        when(customerRepository.findById(request.getCustomerId())).thenReturn(Optional.of(customer));
        when(creditCardMapper.toEntity(request)).thenReturn(creditCard);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.entityToDto(customer)).thenReturn(expectedDto);

        // when
        CustomerDto result = customerService.updateCreditCardInfoByCustomerId(request);

        // then
        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());

        verify(customerRepository).findById(request.getCustomerId());
        verify(creditCardMapper).toEntity(request);
        verify(customerRepository).save(customer);
        verify(customerMapper).entityToDto(customer);
    }

    @Test
    void updateCreditCardInfoByCustomerId_CardNumberAlreadyExists() {
        // given
        Long customerId = 1L;
        UpdateCreditCardInfoRequest request = new UpdateCreditCardInfoRequest(customerId, "1234567890123456",
                544L, LocalDate.MAX);
        Customer customer = new Customer(customerId, "arda@gmail.com", "Arda",
                Collections.emptySet(), Collections.emptySet());
        CustomerDto customerDto = new CustomerDto(customerId, "arda@gmail.com", "Arda",
                Collections.emptySet(), Collections.emptySet());
        CreditCard existingCreditCard = new CreditCard(1L, "1234567890123456", 544L,
                LocalDate.MAX, customer, Collections.emptySet());
        customerDto.setCreditCards(Set.of(existingCreditCard));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.entityListToDtoList(List.of(customer))).thenReturn(List.of(customerDto));

        // then
        assertThrows(AlreadyExistException.class, () -> customerService.updateCreditCardInfoByCustomerId(request));
    }

    @Test
    void findByCustomerId() {
        // given
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "arda@gmail.com", "Arda",
                Collections.emptySet(), Collections.emptySet());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.entityToDto(customer)).thenReturn(new CustomerDto(customerId,
                "arda@gmail.com", "Arda", Collections.emptySet(), Collections.emptySet()));

        // when
        CustomerDto result = customerService.findByCustomerId(customerId);

        // then
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("Arda", result.getName());
        assertEquals("arda@gmail.com", result.getEmail());
    }

    @Test
    void findByCustomerId_CustomerNotFound() {
        // given
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> customerService.findByCustomerId(customerId));
    }

    @Test
    void findEntityByCustomerId() {
        // given
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "arda@gmail.com", "Arda",
                Collections.emptySet(), Collections.emptySet());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        Customer result = customerService.findEntityByCustomerId(customerId);

        // then
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("Arda", result.getName());
        assertEquals("arda@gmail.com", result.getEmail());
    }
}