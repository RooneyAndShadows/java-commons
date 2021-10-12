/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rands.java.commons.crypto;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author mihail
 */
public class Sha256Hasher {

    private static String getStringFromByteArray(byte[] array) {
        return String.format("%040x", new BigInteger(1, array));
    }

    public static String getSha256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        String hashed = getStringFromByteArray(hashBytes);
        return hashed;
    }
}
