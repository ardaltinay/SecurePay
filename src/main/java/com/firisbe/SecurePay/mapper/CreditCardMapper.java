package com.firisbe.SecurePay.mapper;

import com.firisbe.SecurePay.dto.CreditCardDto;
import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.model.request.UpdateCreditCardInfoRequest;
import org.springframework.stereotype.Component;

@Component
public class CreditCardMapper {

    public CreditCardDto toDto(UpdateCreditCardInfoRequest request) {
        return CreditCardDto.builder()
                .cardNumber(request.getCardNumber())
                .cvvNumber(request.getCvvNumber())
                .expireDate(request.getExpireDate())
                .build();
    }

    public CreditCard toEntity(UpdateCreditCardInfoRequest request) {
        return CreditCard.builder()
                .encryptedCardNumber(request.getCardNumber())
                .cvvNumber(request.getCvvNumber())
                .expireDate(request.getExpireDate())
                .build();
    }

    public CreditCardDto entityToDto(CreditCard entity) {
        return CreditCardDto.builder()
                .id(entity.getId())
                .cardNumber(entity.getEncryptedCardNumber())
                .cvvNumber(entity.getCvvNumber())
                .expireDate(entity.getExpireDate())
                .build();
    }

}
