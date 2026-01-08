package org.example.shop.order.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.shop.goods.dto.Good;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "shop_order_detail", indexes = {
        @Index(name = "order_detail_order_id_index",
                columnList = "order_id"),
        @Index(name = "order_detail_goods_id_index",
                columnList = "goods_id")})
@Comment("订单详情表")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("订单详情ID")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("关联的订单ID")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Comment("关联的商品ID")
    @JoinColumn(name = "goods_id", nullable = false)
    private Good goods;

    @Comment("商品数量")
    @Column(name = "goods_number", nullable = false)
    private Integer goodsNumber;


}