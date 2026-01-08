package org.example.shop.order.service;

import org.example.shop.goods.dao.CartRepository;
import org.example.shop.goods.dao.GoodRepository;
import org.example.shop.goods.dto.Cart;
import org.example.shop.goods.dto.Good;
import org.example.shop.order.dao.OrderDetailRepository;
import org.example.shop.order.dao.OrderRepository;
import org.example.shop.order.dto.Order;
import org.example.shop.order.dto.OrderDetail;
import org.example.shop.sys.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private CartRepository cartRepository;

    /**
     * 将购物车内容添加至订单
     * @param userId 用户ID
     * @param _cartIds 购物车条目ID列表
     * @return 结果
     */
    public boolean addOrder(Integer userId, Iterable<Integer> _cartIds) {
        // 筛选指定用户关联的购物车条目
        List<Cart> logCarts = cartRepository.findAllById(_cartIds);
        List<Cart> carts = logCarts.stream().filter(c -> userId.equals(c.getUser().getId())).toList();
        if (carts.isEmpty()) {return false;}
        // 计算总价
        double totalPrice = carts.stream().mapToDouble(c -> c.getGoodsNumber() * c.getGoods().getCost()).sum();
        User user = new User();
        user.setId(userId);
        // 保存订单信息
        Order order = new Order();
        order.setUser(user);
        order.setCost(totalPrice);
        order.setState(1);
        order.setCreatetime(Instant.now());
        Order o = orderRepository.save(order);
        // 保存订单详情信息
        List<OrderDetail> orderDetailList = new ArrayList<>(carts.size());
        for (Cart c : carts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(o);
            orderDetail.setGoodsNumber(c.getGoodsNumber());
            orderDetail.setGoods(c.getGoods());
            orderDetailList.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetailList);
        // 更新销量
        List<Integer> goodIds = carts.stream().map(c -> c.getGoods().getId()).toList();
        List<Integer> goodNumbers = carts.stream().map(Cart::getGoodsNumber).toList();
        List<Good> goodList = goodRepository.findAllById(goodIds);
        for (int i = 0; i < goodList.size(); i++) {
            Good g = goodList.get(i);
            g.setSales(g.getSales() + goodNumbers.get(i));
        }
        goodRepository.saveAll(goodList);
        // 删除购物车记录
        cartRepository.deleteAll(carts);
        return true;
    }

    /**
     * 全部订单列表
     * @param pageRequest 分页器
     * @return 订单列表
     */
    public List<Order> orderListAll(PageRequest pageRequest) {
        List<Order> list = orderRepository.findAll(pageRequest).getContent();
        if (!list.isEmpty()) {
            for (Order o : list) {
                o.getUser().setPassword(null);
            }
        }
        return list;
    }

    /**
     * 根据用户ID查找订单列表
     * @param userId 用户ID
     * @param pageRequest 分页器
     * @return 订单列表
     */
    public List<Order> orderListByUserId(Integer userId, PageRequest pageRequest) {
        List<Order> list = orderRepository.findAllByUserId(userId, pageRequest).getContent();
        if (!list.isEmpty()) {
            for (Order o : list) {
                o.getUser().setPassword(null);
            }
        }
        return list;
    }

    /**
     * 订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    public Order findOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(new Order());
        if (order.getId() != null) {
            order.setOrderDetailList(orderDetailRepository.findAllByOrderId(order.getId()));
        }
        return order;
    }

    public Order findOrderByIdAndUserId(Integer orderId, Integer userId) {
        Order order = orderRepository.findById(orderId).orElse(new Order());
        if (order.getId() != null && userId.equals(order.getUser().getId())) {
            List<OrderDetail> details = orderDetailRepository.findAllByOrderId(order.getId());
            order.setOrderDetailList(details);
            order.getUser().setPassword(null);
        }
        return order;
    }
}
