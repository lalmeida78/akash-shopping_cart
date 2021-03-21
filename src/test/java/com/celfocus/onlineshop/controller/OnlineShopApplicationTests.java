package com.celfocus.onlineshop.controller;

import com.celfocus.onlineshop.model.Cart;
import com.celfocus.onlineshop.model.CartItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

public class OnlineShopApplicationTests extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * Test method to test the  scenario when no cart available in database
     *
     * @throws Exception
     */
    @Test
    public void test_getCarts() throws Exception {
        String uri = "/carts";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

    }

    /**
     * Test method to
     * create the cart
     *
     * @throws Exception
     */
    @Test
    public void test_createCart() throws Exception {
        test_deleteCart();
        String uri = "/user/AK1010/cart";
        Cart cart = new Cart();
        cart.setAddress("Lucknow");
        CartItem item = new CartItem();
        item.setName("Keyborad");
        item.setDescription("Keyborad desc");
        item.setPrice(BigDecimal.TEN);
        item.setQuantity(2);
        cart.addItemsItem(item);

        String inputJson = super.mapToJson(cart);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Cart cart1 = mapFromJson(content, Cart.class);
        Assert.assertNotNull(cart1.getId());
        Assert.assertEquals(201, status);

    }

    /**
     * Test method to test the  scenario when cart available in database for user
     *
     * @throws Exception
     */
    @Test
    public void test_getCartsForUser() throws Exception {
        String uri = "/user/AK1010/cart";
       test_createCart();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

    }

    /**
     * Test to delete the cart for given user
     *
     * @throws Exception
     */
    @Test
    public void test_deleteCart() throws Exception {
        String uri = "/user/AK1010/cart";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

    }


    @Test
    public void test_addItemInCart() throws Exception {
        String uri = "/user/AK1010/cart";
        Cart cart = new Cart();
        cart.setAddress("Lucknow");
        CartItem item = new CartItem();
        item.setName("Keyborad");
        item.setDescription("Keyborad desc");
        item.setPrice(BigDecimal.TEN);
        item.setQuantity(2);
        cart.addItemsItem(item);

        String inputJson = super.mapToJson(cart);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Cart cart1 = mapFromJson(content, Cart.class);
        String cartId = cart1.getId();

        String updateUrl = "/user/AK1010/cart/" + cartId + "/item";

        String inputUpdatedJson = super.mapToJson(item);
        MvcResult mvcUpdateResult = mvc.perform(MockMvcRequestBuilders.post(updateUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputUpdatedJson)).andReturn();

        int status = mvcUpdateResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

    }

    @Test
    public void test_deleteItemInCart() throws Exception {
        String uri = "/user/AK1010/cart";
        Cart cart = new Cart();
        cart.setAddress("Lucknow");
        CartItem item = new CartItem();
        item.setName("Keyborad");
        item.setDescription("Keyborad desc");
        item.setPrice(BigDecimal.TEN);
        item.setQuantity(2);
        CartItem mouseItem = new CartItem();
        mouseItem.setName("Mouse");
        mouseItem.setDescription("MOuse desc");
        mouseItem.setPrice(BigDecimal.TEN);
        mouseItem.setQuantity(2);
        cart.addItemsItem(item);

        String inputJson = super.mapToJson(cart);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Cart cart1 = mapFromJson(content, Cart.class);
        String cartId = cart1.getId();
        Optional<CartItem> addedItem = cart1.getItems().stream().findFirst();
        String updateUrl = "/user/AK1010/cart/" + cartId + "/item/" + addedItem.get().getId();

        MvcResult mvcDeleteResult = mvc.perform(MockMvcRequestBuilders.delete(updateUrl)).andReturn();
        int status = mvcDeleteResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

    }


}
