package org.example.shop.sys.service;

import org.example.shop.sys.dao.UserRepository;
import org.example.shop.sys.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 用户登录
     * @param username 账号
     * @param password 密码
     * @return 用户信息
     */
    public User login(String username, String password) {
        List<User> users = userRepository.findByUsernameAndPassword(username, password);
        if (!users.isEmpty() && users.get(0).getState() == 1) {
            return users.get(0);
        }
        return null;
    }

    /**
     * 管理员登录
     * @param username 账号
     * @param password 密码
     * @return 用户信息
     */
    public User adminLogin(String username, String password) {
        List<User> users = userRepository.findByUsernameAndPassword(username, password);
        if (!users.isEmpty() && users.get(0).getState() == 2) {
            return users.get(0);
        }
        return null;
    }

    /**
     * 用户注销登录
     * @param username 账号
     * @return 注销结果
     */
    public boolean logout(String username) {
        return true;
    }

    /**
     * 用户注册
     * @param username 账号
     * @param password 密码
     * @return 注册结果
     */
    public boolean register(String username, String password) {
        List<User> userByUsername = userRepository.findByUsername(username);
        if (!userByUsername.isEmpty()) {
            // 登录账号已存在
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setState(1);
        userRepository.save(user);
        return true;
    }
}
