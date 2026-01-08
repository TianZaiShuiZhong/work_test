package org.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.CommandLineRunner updateData(org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.update("UPDATE shop_goods SET goods_image = 'images/airPods.jpg', goods_image_detail = 'images/airPods_detail.jpg' WHERE goods_name = 'AirPods'");
            jdbcTemplate.update("UPDATE shop_goods SET goods_image = 'images/BeatsX_1.jpg', goods_image_detail = 'images/BeatsX_1_detail.jpg' WHERE goods_name = 'BeatsX_1'");
            jdbcTemplate.update("UPDATE shop_goods SET goods_image = 'images/BeatsX_2.jpg', goods_image_detail = 'images/BeatsX_2_detail.jpg' WHERE goods_name = 'BeatsX_2'");
            jdbcTemplate.update("UPDATE shop_goods SET goods_image = 'images/sonos.jpg', goods_image_detail = 'images/sonos_detail.jpg' WHERE goods_name = 'Sonos耳机'");
            jdbcTemplate.update("UPDATE shop_goods SET goods_image = 'images/bo_1.jpg', goods_image_detail = 'images/bo_1_detail.jpg' WHERE goods_name = 'B&O蓝牙耳机'");
            jdbcTemplate.update("UPDATE shop_goods SET goods_image = 'images/bo_2.jpg', goods_image_detail = 'images/bo_2_detail.jpg' WHERE goods_name = 'B&O品牌耳机'");
            
            // 初始化管理员账号
            Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM shop_user WHERE user_name = 'admin'", Integer.class);
            if (count != null && count == 0) {
                 jdbcTemplate.update("INSERT INTO shop_user (user_name, user_password, user_nickname, user_state) VALUES ('admin', '123456', 'Admin', 2)");
                 System.out.println("--------------Admin user created successfully!----------------");
            }

            System.out.println("--------------Images updated successfully!----------------");
        };
    }

}
