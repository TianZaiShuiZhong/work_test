package org.example.shop.goods.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "shop_goods")
@Comment("商品表")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("商品ID")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Comment("商品名称")
    @Column(name = "goods_name", nullable = false, length = 100)
    private String name;

    @Comment("商品品牌")
    @Column(name = "goods_brand", length = 30)
    private String brand;

    @Comment("商品销量")
    @ColumnDefault("0")
    @Column(name = "goods_sales", nullable = false)
    private Integer sales;

    @Comment("商品价格")
    @Column(name = "goods_cost", nullable = false)
    private Double cost;

    @Comment("商品颜色")
    @Column(name = "goods_color", length = 30)
    private String color;

    @Comment("商品封面图")
    @Column(name = "goods_image")
    private String image;

    @Comment("商品详情图")
    @Column(name = "goods_image_detail")
    private String imageDetail;

    @Comment("商品状态，0-下架，1-上架")
    @ColumnDefault("0")
    @Column(name = "goods_state", nullable = false)
    private Integer state;


}