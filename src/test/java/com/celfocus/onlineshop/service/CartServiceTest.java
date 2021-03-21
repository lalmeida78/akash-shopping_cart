package com.celfocus.onlineshop.service;


import com.celfocus.onlineshop.dao.CartDao;
import com.celfocus.onlineshop.model.Cart;
import com.celfocus.onlineshop.model.CartItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    CartDao cartDao;

    @Before
    public void setup() {

    }

    @Test
    public void test_createCart() {
        List<com.celfocus.onlineshop.entity.Cart> entityList = cartDao.findByOwnerId("AK101");
        if(!CollectionUtils.isEmpty(entityList)){
            cartDao.delete("AK101");
        }
        Cart result = createCart();
        Assert.assertNotNull(result.getId());
    }

    @Test
    public void test_getAllCarts() {
        test_createCart();
        List<Cart> carts = cartService.findAll();
        Assert.assertNotNull(carts);
    }

    @Test
    public void test_getCartsForUser() {
        test_createCart();
        List<Cart> carts = cartService.findByOwnerId("AK101");
        Assert.assertNotNull(carts);
    }

    @Test
    public void test_deleteCart() {
        cartService.deleteCart("AK101");
        List<com.celfocus.onlineshop.entity.Cart> list = cartDao.findByOwnerId("AK101");
        Assert.assertFalse(list.size()>0);
    }


    private Cart createCart() {
        Cart c = new Cart();
        c.setAddress("Test");
        CartItem item = new CartItem();
        item.setName("test");
        item.setPrice(BigDecimal.TEN);
        item.setDescription("test desc");
        item.setQuantity(1);
        List<CartItem> itemList = new ArrayList<>();
        itemList.add(item);
        c.setItems(itemList);
        Cart result = cartService.createCart("AK101", c);
        return result;
    }

    public void test_addItemInCart(){
        CartItem item = new CartItem();
        item.setName("test");
        item.setPrice(BigDecimal.TEN);
        item.setDescription("test desc");
        item.setQuantity(1);
        List<Cart> carts = cartService.findByOwnerId("AK1010");
        Cart cartToBeUpdated = carts.stream().findFirst().get();
        Cart result = cartService.addItemWithCart(cartToBeUpdated.getUserId(),cartToBeUpdated.getId(),item);
        Assert.assertNotNull(result);

    }


}