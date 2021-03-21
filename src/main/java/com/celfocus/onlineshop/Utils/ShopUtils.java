package com.celfocus.onlineshop.Utils;

import java.util.Random;

public  class ShopUtils {

    public static String generateOrderId(){
        Random random = new Random();
        return "CA000" + random.nextInt(100);
    }
}
