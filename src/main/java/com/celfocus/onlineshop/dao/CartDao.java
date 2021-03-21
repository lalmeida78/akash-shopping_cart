package com.celfocus.onlineshop.dao;

import com.celfocus.onlineshop.entity.Cart;

import java.time.OffsetDateTime;
import java.util.List;

public interface CartDao {

    List<Cart> findByOwnerId(String ownerId) ;

    Cart createCart(String userId, Cart cart)  ;

    List<Cart> findCartWithFilterParameter(String ownerId, String status, OffsetDateTime creationDateTime) ;

    void delete(String ownerId) ;

    List<Cart> findAll() ;

    Cart saveOrUpdateCart(Cart entity) ;

    Cart findByOrderIdAndOwnerId(String orderId, String ownerId) ;

}
