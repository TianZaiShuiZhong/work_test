package org.example.shop.order.dao;

import org.example.shop.order.dto.ShopOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {
    Page<ShopOrder> findAllByUserId(int userId, Pageable pageable);
}