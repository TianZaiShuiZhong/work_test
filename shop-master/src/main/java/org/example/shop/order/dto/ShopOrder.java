package org.example.shop.order.dto;

import jakarta.persistence.*;
import lombok.*;
import org.example.shop.sys.dto.ShopUser;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shop_order", schema = "shop")
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_cost", nullable = false)
    private Double cost;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_user_id", nullable = false)
    private ShopUser user;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "order_create_time", nullable = false)
    private Instant createTime;

    @ColumnDefault("0")
    @Column(name = "oder_state", nullable = false)
    private Integer state;

    @Transient
    private List<ShopOrderDetail> orderDetails;
}