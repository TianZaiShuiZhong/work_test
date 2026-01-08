package org.example.shop.goods.service;

import org.example.shop.goods.dao.CartRepository;
import org.example.shop.goods.dto.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    /**
     * 通过用户ID获取用户购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    public List<Cart> findByUserId(Integer userId) {
        List<Cart> list = cartRepository.findByUserId(userId);
        for (Cart s : list) {
            s.getUser().setPassword(null);
        }
        return list;
    }

    /**
     * 删除购物车选项
     * @param ids 选项ID
     * @param userId 用户ID
     * @return 结果
     */
    public boolean deleteCart(List<Integer> ids, Integer userId) {
        List<Cart> byUserId = cartRepository.findByUserId(userId);
        List<Integer> cartIdsByUserId = new ArrayList<>();
        byUserId.forEach(sc -> cartIdsByUserId.add(sc.getId()));
        List<Integer> finalList = ids.stream().filter(cartIdsByUserId::contains).collect(Collectors.toList());
        if (!finalList.isEmpty()) {
            cartRepository.deleteAllById(finalList);
        }
        return true;
    }

    /**
     * 添加购物车记录
     *
     * @param cart 购物车条目
     * @return 操作结果
     */
    public boolean addCart(Cart cart) {
        Cart log = cartRepository.findByUserIdAndGoodsId(cart.getUser().getId(), cart.getGoods().getId());
        if (log != null && log.getId() != null) {
            log.setGoodsNumber(log.getGoodsNumber() + 1);
            cart = log;
        }
        cartRepository.save(cart);
        return true;
    }

    /**
     * 修改购物车记录
     *
     * @param cart 购物车条目
     * @return 操作结果
     */
    public boolean saveCart(Cart cart) {
        Optional<Cart> byId = cartRepository.findById(cart.getId());
        if (byId.isPresent()) {
            if (cart.getGoodsNumber() < 1) {
                cartRepository.delete(cart);
            } else {
                cartRepository.save(cart);
            }
            return true;
        } else {
            return false;
        }
    }
}
