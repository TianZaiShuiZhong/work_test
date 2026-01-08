package org.example.shop.sys.service;

import org.example.shop.sys.dao.UserRepository;
import org.example.shop.sys.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 添加用户
     * @param user 用户对象
     * @return 添加结果
     */
    public boolean saveUser(User user) {
        user.setState(1);
        userRepository.save(user);
        return true;
    }

    /**
     * 删除用户
     * <p>
     * 考虑到用户可能与购物车、订单关联，不能真的把用户删掉。
     * 故此删除逻辑是将其状态位置0。状态字段含义如下：0-异常，1-正常。
     * </p>
     *
     * @param id 用户ID
     * @return 默认成功
     */
    public boolean deleteUser(int id) {
        User user = userRepository.findById(id).orElse(new User());
        if (user.getUsername() == null) {
            return false;
        }
        user.setState(0);
        userRepository.save(user);
        return true;
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改后的用户信息
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 查找用户
     * @param id 用户ID
     * @return 用户信息
     */
    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 查找用户，不带密码数据
     * @param id 用户ID
     * @return 用户信息
     */
    public User findUserSafelyById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            //不返回登录密码
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 分页读取所有用户列表
     * <p>
     * 需要注意的是,JPA分页从第0页开始
     * </p>
     * @param pageable 分页器
     * @return 用户列表
     */
    public List<User> findUsersByPage(Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).getContent();
        for (User u : users) {
            u.setPassword(null);
        }
        return users;
    }
}
