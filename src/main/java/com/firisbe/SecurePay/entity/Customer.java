package com.firisbe.SecurePay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Invalid email format!")
    @NotBlank(message = "Email can not be blank!")
    private String email;

    @NotBlank(message = "Name can not be blank!")
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<CreditCard> creditCards;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Payment> payments;
}
