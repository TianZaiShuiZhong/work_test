package org.example.shop.goods.dao;

import org.example.shop.goods.dto.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCartRepository extends JpaRepository<ShopCart, Integer> {
    List<ShopCart> findShopCartByUserId(Integer userId);
    void deleteShopCartByUserId(Integer userId);
    ShopCart findShopCartByUserIdAndGoodsId(Integer userId, Integer goodsId);
    void deleteAllByGoodsId(Integer goodsId);
}