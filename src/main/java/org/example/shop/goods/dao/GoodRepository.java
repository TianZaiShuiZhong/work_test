package org.example.shop.goods.dao;

import org.example.shop.goods.dto.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Integer> {
    Good findByName(String name);

    Page<Good> findAllByColor(String color, Pageable pageable);

    Page<Good> findAllByBrand(String brand, Pageable pageable);

    Page<Good> findAllByColorAndBrand(String color, String brand, Pageable pageable);
}