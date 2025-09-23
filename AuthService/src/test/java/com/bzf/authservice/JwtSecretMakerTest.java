package com.bzf.authservice;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.security.Keys;

public class JwtSecretMakerTest {
	
	@Test
	public void generateSecretKey() {
		SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String base64Key = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Secret Key: " + base64Key);
	}

}
