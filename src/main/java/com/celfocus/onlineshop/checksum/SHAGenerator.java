package com.celfocus.onlineshop.checksum;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SHAGenerator implements CheckSumGeneratorStrategy {

    @Override
    public byte[] digest(byte[] input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return digest.digest(input);
    }
}
