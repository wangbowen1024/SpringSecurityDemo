package com.securitydemo.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SecurityConfigTest {

    @Test
    public void testBCryptPasswordEncoder() {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        final boolean matches = passwordEncoder.matches("123456", encode);
        System.out.println(matches);
    }
}
