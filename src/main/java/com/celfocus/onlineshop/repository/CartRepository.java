package com.celfocus.onlineshop.repository;

import com.celfocus.onlineshop.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    List<Cart> findByOwnerId(String ownerId);

    void deleteByOwnerId(String ownerId);

    List<Cart> findAll();

    Cart findByOrderIdAndOwnerId(String cartId, String ownerId);

}
