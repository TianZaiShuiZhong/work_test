package org.example.shop.goods.dao;

import org.example.shop.goods.dto.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(Integer userId);
    Cart findByUserIdAndGoodsId(Integer userId, Integer goodsId);
}