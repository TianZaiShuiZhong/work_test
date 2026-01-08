package org.example.shop.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.shop.order.dto.ShopOrderDetail;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shop_goods", schema = "shop")
public class ShopGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "goods_name", nullable = false)
    private String name;

    @Column(name = "goods_brand", nullable = false, length = 100)
    private String brand;

    @ColumnDefault("0")
    @Column(name = "goods_sales", nullable = false)
    private Integer sales;

    @ColumnDefault("0")
    @Column(name = "goods_cost", nullable = false)
    private Double cost;

    @Column(name = "goods_color", length = 50)
    private String color;

    @Column(name = "goods_image")
    private String image;

    @Column(name = "goods_image_detail", length = 50)
    private String imageDetail;

    @ColumnDefault("1")
    @Column(name = "goods_state", nullable = false)
    private Integer state;
}