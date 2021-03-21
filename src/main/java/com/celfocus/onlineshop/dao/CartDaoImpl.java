package com.celfocus.onlineshop.dao;

import com.celfocus.onlineshop.Utils.ShopUtils;
import com.celfocus.onlineshop.entity.Cart;
import com.celfocus.onlineshop.repository.CartRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    @Autowired
    CartRepository cartRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Cart> findByOwnerId(String ownerId) {
        return cartRepository.findByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public Cart createCart(String userId, Cart cart) {
        cart.setOwnerId(userId);
        cart.setOrderId(ShopUtils.generateOrderId());
        cart = cartRepository.save(cart);
        return cart;
    }

    @Override
    @Transactional
    public List<Cart> findCartWithFilterParameter(String ownerId, String orderId, OffsetDateTime createdAfter) {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cart.class);
        if (!StringUtils.isEmpty(ownerId)) {
            criteria.add(Restrictions.and(Restrictions.eq("ownerId", ownerId)));
        }
        if (!StringUtils.isEmpty(orderId)) {
            criteria.add(Restrictions.and(Restrictions.eq("orderId", orderId)));
        }
        if (createdAfter != null) {
            criteria.add(Restrictions.and(Restrictions.ge("createdAt", createdAfter.toLocalDateTime())));
        }
        return criteria.list();
    }

    @Override
    @Transactional
    public void delete(String ownerId) {
        cartRepository.deleteByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    @Transactional
    public Cart saveOrUpdateCart(Cart entity) {
         return cartRepository.save(entity);
    }

    @Override
    @Transactional
    public Cart findByOrderIdAndOwnerId(String cartId, String ownerId) {
        return cartRepository.findByOrderIdAndOwnerId(cartId, ownerId);

    }

}
