package com.celfocus.onlineshop.checksum;

import com.celfocus.onlineshop.Exception.ChecksumGenerationException;
import com.celfocus.onlineshop.model.Cart;
import com.celfocus.onlineshop.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class CheckSumGenerator {

    @Autowired
    private MD5Generator md5Generator;

    @Autowired
    private SHAGenerator shaGenerator;

    public byte[] getCheckSum(Cart cart, String algorithm) throws ChecksumGenerationException {
        try {
            byte[] inputArray = generateByteArrayFromCart(cart);
            switch (algorithm) {
                case "MD5":
                    return md5Generator.digest(inputArray, algorithm);
                case "SHA-1":
                case "SHA-256":
                case "SHA-512":
                    return shaGenerator.digest(inputArray, algorithm);
                default:
                    return shaGenerator.digest(inputArray, "SHA-512");

            }
        } catch (Exception e) {
            throw new ChecksumGenerationException("Check sum generation issue.");
        }
    }

    private byte[] generateByteArrayFromCart(Cart cart) {
        StringBuilder builder = new StringBuilder(cart.getAddress());
        builder.append(cart.getId());
        for (CartItem item : cart.getItems()) {
            builder.append(item.getId()).append(item.getDescription()).append(item.getName()).append(item.getPrice()).append(item.getQuantity());
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

}
