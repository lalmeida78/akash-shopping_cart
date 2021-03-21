package com.celfocus.onlineshop.mapper;

import com.celfocus.onlineshop.entity.Cart;
import com.celfocus.onlineshop.entity.Item;
import com.celfocus.onlineshop.model.CartItem;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CartDomainToEntityMapper {

    CartDomainToEntityMapper INSTANCE = Mappers.getMapper(CartDomainToEntityMapper.class);

    void mapToExistingEntity(@MappingTarget Item item, CartItem cartItem);

    @Mappings({
            @Mapping(target = "id", source = "cartItem.id"),
            @Mapping(target = "name", source = "cartItem.name"),
            @Mapping(target = "description", source = "cartItem.description"),
            @Mapping(target = "price", source = "cartItem.price"),
            @Mapping(target = "quantity", source = "cartItem.quantity"),
            @Mapping(target = "cart", source = "cart")

    })
    Item mapToItem(CartItem cartItem, Cart cart);

    default Cart mapToEntityCart(com.celfocus.onlineshop.model.Cart model) {
        Cart cart = new Cart();
        cart.setAddress(model.getAddress());
        cart.setItems(mapToItems(model.getItems(), cart));
        return cart;
    }

    default List<Item> mapToItems(List<CartItem> cartItems, Cart cart) {
        List<Item> items = new ArrayList<>();
        for (CartItem item : cartItems) {
            Item itemEntity = new Item();
            itemEntity.setName(item.getName());
            itemEntity.setDescription(item.getDescription());
            itemEntity.setPrice(item.getPrice());
            itemEntity.setQuantity(item.getQuantity());
            itemEntity.setCart(cart);
            items.add(itemEntity);
        }
        return items;
    }

}
