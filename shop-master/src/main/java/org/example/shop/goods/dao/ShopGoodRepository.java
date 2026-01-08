package org.example.shop.goods.dao;

import org.example.shop.goods.dto.ShopGood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopGoodRepository extends JpaRepository<ShopGood, Integer> {
    ShopGood findByName(String name);
    Page<ShopGood> findAllByState(Integer state, Pageable pageable);
    Page<ShopGood> findAllByColorAndState(String color, Integer state, Pageable pageable);
    Page<ShopGood> findAllByBrandAndState(String brand, Integer state, Pageable pageable);
    Page<ShopGood> findAllByColorAndBrandAndState(String color, String brand, Integer state, Pageable pageable);
}