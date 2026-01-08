package org.example.shop.order.dao;

import org.example.shop.order.dto.ShopOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopOrderDetailRepository extends JpaRepository<ShopOrderDetail, Integer> {
    List<ShopOrderDetail> findAllByOrderId(Integer orderId);
    void deleteByGoodsId(Integer goodsId);
}