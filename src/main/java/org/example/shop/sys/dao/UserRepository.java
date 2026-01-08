package org.example.shop.sys.dao;

import org.example.shop.sys.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUsername(String username);
    List<User> findByUsernameAndPassword(String username, String password);
}