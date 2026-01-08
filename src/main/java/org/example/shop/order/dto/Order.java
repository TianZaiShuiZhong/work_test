package org.example.shop.order.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.shop.sys.dto.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shop_order", indexes = {@Index(name = "order_user_id_index",
        columnList = "order_user_id")})
@Comment("订单表")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("订单ID")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Comment("关联的用户ID")
    @JoinColumn(name = "order_user_id", nullable = false)
    private User user;

    @Comment("订单总金额")
    @Column(name = "order_cost", nullable = false)
    private Double cost;

    @Comment("订单创建时间")
    //@ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "order_create_time")
    private Instant createtime;

    @Comment("订单状态，0-未支付，1-已支付/未发货，2-已发货，3-已收货，4-已完成，5-已取消")
    //@ColumnDefault("0")
    @Column(name = "order_state", nullable = false)
    private Integer state;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetailList;
}