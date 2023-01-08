package com.securitydemo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class JwtUtilsTest {

    @Test
    void testJwtToken() throws Exception {
        final String jwtToken = JwtUtils.createJWT("123");
        System.out.println(jwtToken);

        final Claims claims = JwtUtils.parseJWT(jwtToken);
        final String subject = claims.getSubject();
        System.out.println(subject);
    }

}