package com.firisbe.SecurePay.util;

import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@Slf4j
public class EncryptionUtil implements AttributeConverter<String, String> {

    private final String KEY = "3453424323423521";

    private final String AES = "AES";

    private final Key key;

    private final Cipher cipher;

    public EncryptionUtil() {
        this.key = new SecretKeySpec(KEY.getBytes(), AES);
        try {
            this.cipher = Cipher.getInstance(AES);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("EncryptionUtil can not initiate with error message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public String convertToDatabaseColumn(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("Error while encrypting db value with error message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String encryptedValue) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedValue)));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            log.error("Error while decrypting db value with error message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
