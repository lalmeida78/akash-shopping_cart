package com.celfocus.onlineshop.service;

import com.celfocus.onlineshop.Exception.CartNotFoundException;
import com.celfocus.onlineshop.Exception.CartProcessingException;
import com.celfocus.onlineshop.Exception.ChecksumGenerationException;
import com.celfocus.onlineshop.Exception.MutationNotAllowedException;
import com.celfocus.onlineshop.Utils.ShopConstant;
import com.celfocus.onlineshop.checksum.CheckSumGenerator;
import com.celfocus.onlineshop.dao.CartDao;
import com.celfocus.onlineshop.entity.Item;
import com.celfocus.onlineshop.mapper.CartDomainToEntityMapper;
import com.celfocus.onlineshop.mapper.CartEntityToDomainMapper;
import com.celfocus.onlineshop.model.Cart;
import com.celfocus.onlineshop.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    CheckSumGenerator checkSumGenerator;

    @Value("${security.checksum.type}")
    String checkSumType;


    @Override
    public List<Cart> findByOwnerId(String userId) throws CartNotFoundException {
        List<com.celfocus.onlineshop.entity.Cart> entityCartList = cartDao.findByOwnerId(userId);
        if (CollectionUtils.isEmpty(entityCartList)) {
            throw new CartNotFoundException("Cart is not available for provided user");
        }
        return mapCartsWithCheckSum(entityCartList);
    }

    @Override
    public List<Cart> getCartsWithFilter(String userId, OffsetDateTime createdAfter, String cartId) {
        List<com.celfocus.onlineshop.entity.Cart> entityCartList = cartDao.findCartWithFilterParameter(userId, cartId, createdAfter);
        if (CollectionUtils.isEmpty(entityCartList)) {
            throw new CartNotFoundException("Cart is not available for provided user");
        }
        return mapCartsWithCheckSum(entityCartList);
    }


    @Override
    public Cart createCart(String userId, Cart cart) throws ChecksumGenerationException {
        List<com.celfocus.onlineshop.entity.Cart> entityCartList = cartDao.findByOwnerId(userId);
        if (!CollectionUtils.isEmpty(entityCartList)) {
            List<com.celfocus.onlineshop.entity.Cart> cartsInAvailableStatus =
                    entityCartList.stream().filter(entityCart -> !isExpired(entityCart.getCreatedAt())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cartsInAvailableStatus)) {
                throw new CartProcessingException("There is active card in system . Please delete or  update the existing card.");
            }
        }
        Cart cartModel = CartEntityToDomainMapper.INSTANCE.mapToCartModel(cartDao.createCart(userId, CartDomainToEntityMapper.INSTANCE.mapToEntityCart(cart)));
        return mapCartWithCheckSum(cartModel);
    }

    @Override
    public void deleteCart(String ownerId) {
        List<com.celfocus.onlineshop.entity.Cart> entityCartList = cartDao.findByOwnerId(ownerId);
        if (CollectionUtils.isEmpty(entityCartList)) {
            throw new CartNotFoundException("Cart is not available for provided user");
        }
        cartDao.delete(ownerId);
    }

    @Override
    public List<Cart> findAll() {
        List<com.celfocus.onlineshop.entity.Cart> entityCartList = cartDao.findAll();
        if (CollectionUtils.isEmpty(entityCartList)) {
            throw new CartNotFoundException("No carts available in the system");
        }
        return mapCartsWithCheckSum(entityCartList);
    }


    @Override
    public Cart addItemWithCart(String userId, String cartId, CartItem cartItem) throws CartNotFoundException, MutationNotAllowedException {
        com.celfocus.onlineshop.entity.Cart cart = cartDao.findByOrderIdAndOwnerId(cartId, userId);
        validateCart(cart);
        Item entityItem = CartDomainToEntityMapper.INSTANCE.mapToItem(cartItem, cart);
        cart.getItems().add(entityItem);
        Cart result = CartEntityToDomainMapper.INSTANCE.mapToCartModel(cartDao.saveOrUpdateCart(cart));
        return mapCartWithCheckSum(result);
    }


    @Override
    public Cart deleteItemWithCart(String userId, String cartId, Long itemId) throws CartNotFoundException, MutationNotAllowedException {
        com.celfocus.onlineshop.entity.Cart cart = cartDao.findByOrderIdAndOwnerId(cartId, userId);
        validateCart(cart);
        cart.getItems().removeIf(item -> (itemId.compareTo(item.getId()) == 0));
        return CartEntityToDomainMapper.INSTANCE.mapToCartModel(cartDao.saveOrUpdateCart(cart));
    }

    @Override
    public Cart updateItemInCart(String userId, String cartId, Long itemId, CartItem cartItem) throws CartNotFoundException, MutationNotAllowedException {
        com.celfocus.onlineshop.entity.Cart cart = cartDao.findByOrderIdAndOwnerId(cartId, userId);
        validateCart(cart);
        cart.getItems().forEach(item ->
                {
                    if (itemId.compareTo(item.getId()) == 0) {
                        CartDomainToEntityMapper.INSTANCE.mapToExistingEntity(item, cartItem);

                    }
                }
        );
        Cart result = CartEntityToDomainMapper.INSTANCE.mapToCartModel(cartDao.saveOrUpdateCart(cart));
        return result;
    }

    private void validateCart(com.celfocus.onlineshop.entity.Cart cart) throws CartNotFoundException, MutationNotAllowedException {
        if (Objects.isNull(cart)) {
            throw new CartNotFoundException("For the given User Id,Cart Id and item Id , no record exist.");
        }
        if (isExpired(cart.getCreatedAt())) {
            throw new MutationNotAllowedException("Cart expired");
        }
    }

    private boolean isExpired(LocalDateTime cartCreationDate) {
        final LocalDateTime validExpireDate = LocalDateTime.now().minusHours(ShopConstant.EXPIARY_HOURS_DIFF);
        return cartCreationDate.isBefore(validExpireDate);
    }

    private List<Cart> mapCartsWithCheckSum(List<com.celfocus.onlineshop.entity.Cart> entityCartList) {
        List<Cart> cartList = CartEntityToDomainMapper.INSTANCE.mapToModelCartList(entityCartList);
        cartList.forEach(cart -> cart.setChecksum(checkSumGenerator.getCheckSum(cart, checkSumType)));
        return cartList;
    }

    private Cart mapCartWithCheckSum(Cart cart) {
        cart.setChecksum(checkSumGenerator.getCheckSum(cart, checkSumType));
        return cart;
    }
}
