package org.example.shop.goods.controller;

import io.jsonwebtoken.Claims;
import org.example.shop.goods.dto.Cart;
import org.example.shop.goods.dto.Good;
import org.example.shop.goods.service.CartService;
import org.example.shop.goods.vo.CartVO;
import org.example.shop.sys.dto.User;
import org.example.shop.util.JwtUtil;
import org.example.shop.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户购物车列表
     *
     * @param token 用户登录信息
     * @return 固定响应体
     */
    @GetMapping("/list")
    public R list(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.getClaimsFromHeader(token);
        List<Cart> carts = cartService.findByUserId(claims.get("id", Integer.class));
        return R.ok(carts);
    }

    /**
     * 删除购物车信息
     *
     * @param ids 条目ID（批量）
     * @return 固定响应体
     */
    @PostMapping("/delete")
    public R delete(@RequestBody List<Integer> ids, @RequestHeader("Authorization") String token) {
        if (ids == null || ids.isEmpty()) {
            return R.of(400, "参数不正确", null);
        }
        cartService.deleteCart(ids, jwtUtil.getClaimsFromHeader(token).get("id", Integer.class));
        return R.ok(null);
    }

    /**
     * 添加购物车条目
     * @param vo 提供goodsId信息
     * @param token 请求头的Token信息
     * @return 固定响应体
     */
    @PostMapping("/add")
    public R add(@RequestBody CartVO vo, @RequestHeader("Authorization") String token) {
        if (vo == null || vo.getGoodsId() == null) {
            return R.of(400, "参数有误", null);
        }

        Cart cart = new Cart();
        cart.setGoodsNumber(1);
        Good goods = new Good();
        goods.setId(vo.getGoodsId());
        cart.setGoods(goods);

        User user = new User();
        user.setId(jwtUtil.getClaimsFromHeader(token).get("id", Integer.class));
        cart.setUser(user);

        cartService.addCart(cart);
        return R.ok(null);
    }

    /**
     * 修改购物车条目信息
     * @param vo 条目概要信息
     * @param token 请求头Token信息
     * @return 固定响应体
     */
    @PostMapping("/update")
    public R update(@RequestBody CartVO vo, @RequestHeader("Authorization") String token) {
        if (vo == null) {
            return R.of(400, "参数不正确", null);
        }
        Cart cart = new Cart();
        cart.setId(vo.getId());
        cart.setGoodsNumber(vo.getGoodsNumber());

        Good goods = new Good();
        goods.setId(vo.getGoodsId());
        cart.setGoods(goods);

        User user = new User();
        user.setId(jwtUtil.getClaimsFromHeader(token).get("id", Integer.class));
        cart.setUser(user);

        if (cartService.saveCart(cart)) {
            return R.ok(null);
        } else {
            return R.of(400, "更新失败", null);
        }
    }
}
