package com.celfocus.onlineshop.service;

import com.celfocus.onlineshop.Exception.CartNotFoundException;
import com.celfocus.onlineshop.Exception.ChecksumGenerationException;
import com.celfocus.onlineshop.Exception.MutationNotAllowedException;
import com.celfocus.onlineshop.model.Cart;
import com.celfocus.onlineshop.model.CartItem;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface CartService {

    List<Cart> findByOwnerId(String userId) throws CartNotFoundException;

    List<Cart> getCartsWithFilter(String userId, OffsetDateTime date, String cartId);

    Cart createCart(String userId, Cart cart) throws ChecksumGenerationException;

    void deleteCart(String userId);

    List<Cart> findAll();

    Cart addItemWithCart(String userId, String cartId, CartItem cartItem) throws CartNotFoundException, MutationNotAllowedException;

    Cart deleteItemWithCart(String userId, String cartId, Long itemId) throws CartNotFoundException, MutationNotAllowedException;

    Cart updateItemInCart(String userId, String cartId, Long itemId, CartItem cartItem) throws CartNotFoundException, MutationNotAllowedException;


}
