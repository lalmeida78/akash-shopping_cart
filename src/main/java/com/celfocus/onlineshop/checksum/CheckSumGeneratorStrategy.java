package com.celfocus.onlineshop.checksum;

import java.security.NoSuchAlgorithmException;

public interface CheckSumGeneratorStrategy {

    byte[] digest(byte[] input,String algorithm) throws NoSuchAlgorithmException;


}
