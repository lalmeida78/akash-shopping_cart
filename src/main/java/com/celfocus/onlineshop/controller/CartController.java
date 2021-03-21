package com.celfocus.onlineshop.controller;

import com.celfocus.onlineshop.api.CartsApi;
import com.celfocus.onlineshop.model.Cart;
import com.celfocus.onlineshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class CartController implements CartsApi {

    @Autowired
    CartService cartService;

    @Override
    public ResponseEntity<List<Cart>> getAllCarts(String userId, String orderId, OffsetDateTime createdAfter) {
        List<Cart> carts = cartService.getCartsWithFilter(userId,createdAfter,orderId);
        return ResponseEntity.ok(carts);
    }




}