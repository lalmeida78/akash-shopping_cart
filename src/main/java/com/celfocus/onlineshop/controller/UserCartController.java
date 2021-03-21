package com.celfocus.onlineshop.controller;

import com.celfocus.onlineshop.api.UserApi;
import com.celfocus.onlineshop.model.Cart;
import com.celfocus.onlineshop.model.CartItem;
import com.celfocus.onlineshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
public class UserCartController implements UserApi {

    @Autowired
    private CartService cartService;

    @Override
    public ResponseEntity<List<Cart>> getCarts(String id) {
        List<Cart> carts = cartService.findByOwnerId(id);
        return ResponseEntity.ok(carts);
    }

    @Override
    public ResponseEntity<Cart> createCart(String id, Cart cart) {
        return new ResponseEntity<>(cartService.createCart(id, cart), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCartForOwner(String id) {
        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addItemInCart(String id, String cartId, CartItem cartItem) {
        Cart cart = cartService.addItemWithCart(id, cartId, cartItem);
        if (Objects.nonNull(cart)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Void> deleteItemInCart(String id, String cartId, Long itemId) {
        Cart cart = cartService.deleteItemWithCart(id, cartId, itemId);
        if (Objects.nonNull(cart)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Void> updateItemInCart(String id, String cartId, Long itemId, CartItem cartItem) {
        Cart cart = cartService.updateItemInCart(id, cartId, itemId, cartItem);
        if (Objects.nonNull(cart)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
