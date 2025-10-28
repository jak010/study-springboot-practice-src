package com.practic.demo;

import com.practic.demo.config.JasyptConfig;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JasyptConfig.class)
public class EncryptorTest {

    @Autowired
    @Qualifier("jasyptStringEncryptor")
    private StringEncryptor encryptor;


    @Test
    @DisplayName("jasypt 암호화 테스트")
    public void test() {
        String original = "secret";
        String encrypted = encryptor.encrypt(original);
        String decrypted = encryptor.decrypt(encrypted);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        assertEquals(original, decrypted);

    }


}
