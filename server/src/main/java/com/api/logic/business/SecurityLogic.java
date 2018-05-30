package com.api.logic.business;

// #region Imports
import java.util.Date;
import java.util.Properties;

import java.io.UnsupportedEncodingException;
import java.io.IOException;

import java.math.BigInteger;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
// #endregion

public class SecurityLogic {
    private Properties prop = new Properties();

    // #region Constructors
    public SecurityLogic () {
        try {
            // Read properties from external file.
            prop.load(getClass().getResourceAsStream("/config.properties"));
        }
        catch(IOException e) {

        }
    }
    // #endregion

    // #region Tokens
    public String issueAuthToken(int userId) throws UnsupportedEncodingException, JWTCreationException {
        String password = prop.getProperty("security-password");

        Algorithm algorithm = Algorithm.HMAC256(password);

        return JWT.create().withIssuer("auth0").withExpiresAt(new Date(System.currentTimeMillis() + (12 * 60 * 60 * 1000))).withClaim("id", userId).sign(algorithm);
    }

    public int validateToken(String token) throws UnsupportedEncodingException, JWTVerificationException, IOException {
        String password = prop.getProperty("security-password");

        Algorithm algorithm = Algorithm.HMAC256(password);

        //Reusable verifier instance
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaim("id").asInt();
    }
    // #endregion

    // #region Passwords
    public String encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];

        sr.nextBytes(salt);

        return salt;
    }

    private String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();

        if(paddingLength > 0)
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    // #endregion
}
