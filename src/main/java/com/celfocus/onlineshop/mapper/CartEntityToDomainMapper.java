package com.celfocus.onlineshop.mapper;

import com.celfocus.onlineshop.Utils.ShopConstant;
import com.celfocus.onlineshop.entity.Item;
import com.celfocus.onlineshop.model.Cart;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(
        nullValueCheckStrategy=NullValueCheckStrategy.ALWAYS)
public interface CartEntityToDomainMapper {



    CartEntityToDomainMapper INSTANCE = Mappers.getMapper(CartEntityToDomainMapper.class);

    @IterableMapping(qualifiedByName = "mapToCartModel")
    List<Cart> mapToModelCartList(List<com.celfocus.onlineshop.entity.Cart> source);

    @Named("mapToCartModel")
    @Mappings(
            {
                    @Mapping(source = "ownerId", target = "userId"),
                    @Mapping(source = "orderId", target = "id"),
                    @Mapping(target = "creationDate", source = "createdAt", qualifiedByName = "mapToCreationDate"),
                    @Mapping(target = "status", source = "createdAt", qualifiedByName = "mapToStatus"),
                    @Mapping(target="price", source="cartEntity.items" ,qualifiedByName = "mapToPrice")

            }
    )
    Cart mapToCartModel(com.celfocus.onlineshop.entity.Cart cartEntity);

    @Named("mapToCreationDate")
    default OffsetDateTime mapToCreationDate(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

    @Named("mapToStatus")
    default Cart.StatusEnum mapToStatus(LocalDateTime localDateTime) {
        final LocalDateTime validExpireDate = LocalDateTime.now().minusHours(ShopConstant.EXPIARY_HOURS_DIFF);
        if (localDateTime.isAfter(validExpireDate)) {
            return com.celfocus.onlineshop.model.Cart.StatusEnum.AVAILABLE;
        } else {
            return com.celfocus.onlineshop.model.Cart.StatusEnum.EXPIRED;
        }
    }

    @Named("mapToPrice")
    default BigDecimal mapToPrice(List<Item> itemList){
        return itemList.stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
