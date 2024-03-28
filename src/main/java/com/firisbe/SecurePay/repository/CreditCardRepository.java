package com.firisbe.SecurePay.repository;

import com.firisbe.SecurePay.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    @Query("select c from CreditCard c where c.encryptedCardNumber = :cardNumber")
    List<CreditCard> findByCardNumber(@Param("cardNumber") String cardNumber);
}
