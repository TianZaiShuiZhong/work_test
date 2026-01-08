package org.example.shop.goods.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.shop.sys.dto.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "shop_cart", indexes = {
        @Index(name = "cart_user_id_index",
                columnList = "cart_user_id"),
        @Index(name = "cart_goods_id_index",
                columnList = "cart_goods_id")})
@Comment("购物车表")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("购物车记录ID")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Comment("关联的用户ID")
    @JoinColumn(name = "cart_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Comment("关联的商品ID")
    @JoinColumn(name = "cart_goods_id", nullable = false)
    private Good goods;

    @Comment("商品数量")
    //@ColumnDefault("1")
    @Column(name = "cart_goods_number", nullable = false)
    private Integer goodsNumber;


}