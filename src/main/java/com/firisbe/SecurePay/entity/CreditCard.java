package com.firisbe.SecurePay.entity;

import com.firisbe.SecurePay.util.EncryptionUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "credit_cards")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Convert(converter = EncryptionUtil.class)
    private String encryptedCardNumber;

    private Long cvvNumber;

    private LocalDate expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
    private Set<Payment> payments;
}
