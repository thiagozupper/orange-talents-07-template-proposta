package br.com.zupacademy.thiago.proposta.proposta;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String AES = "AES";

    @Value("${attributte.convert.secret}")
    private String SECRET;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        Key key = new SecretKeySpec(SECRET.getBytes(), AES);
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        Key key = new SecretKeySpec(SECRET.getBytes(), AES);
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }
}
