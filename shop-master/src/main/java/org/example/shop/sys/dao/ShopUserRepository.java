package org.example.shop.sys.dao;

import org.example.shop.sys.dto.ShopUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopUserRepository extends JpaRepository<ShopUser, Integer> {
    List<ShopUser> findShopUserByUsernameAndPassword(String username, String password);
    List<ShopUser> findShopUserByUsername(String username);
}