package com.celfocus.onlineshop.dao;

import com.celfocus.onlineshop.entity.Cart;
import com.celfocus.onlineshop.entity.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CartDaoTest {

    @Autowired
    private CartDao cartDao;

    @Before
    public void setup() {

    }

    @Test
    public void test_getAllCarts() {
        List<Cart> carts = cartDao.findAll();
        Assert.assertNotNull(carts);
    }

    @Test
    public void test_getCartsForUser() {
        List<Cart> carts = cartDao.findByOwnerId("AK101");
        Assert.assertNotNull(carts);
    }

    @Test
    public void test_createCart() {
        Cart cart = new Cart();
        cart.setAddress("Test");
        Item item = new Item();
        item.setName("test");
        item.setPrice(BigDecimal.TEN);
        item.setDescription("test desc");
        item.setQuantity(1);
        item.setCart(cart);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        Cart result = cartDao.createCart("AK1010", cart);
        Assert.assertNotNull(result.getOrderId());
    }

    @Test
    public void test_addItemInCart(){
        test_createCart();
        List<Cart> carts = cartDao.findByOwnerId("AK1010");
        Cart cartToBeUpdated = carts.stream().findFirst().get();
        cartToBeUpdated.setAddress("test1");
        Cart result  = cartDao.saveOrUpdateCart(cartToBeUpdated);
        Assert.assertNotNull(result.getOrderId());

    }


    @Test
    public void test_deleteCart() {
        cartDao.delete("AK1010");
        List<Cart> carts = cartDao.findByOwnerId("AK101");
        Assert.assertNotNull(carts);
    }


}