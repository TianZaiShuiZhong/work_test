package org.example.shop.order.dao;

import org.example.shop.order.dto.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByUserId(Integer userId, Pageable pageable);
}