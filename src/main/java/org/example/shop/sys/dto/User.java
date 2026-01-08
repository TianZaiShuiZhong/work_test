package org.example.shop.sys.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "shop_user")
@Comment("用户表")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("用户ID")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Comment("用户名/登陆账号")
    @Column(name = "user_name", nullable = false, length = 50)
    private String username;

    @Comment("登陆密码")
    @Column(name = "user_password", nullable = false, length = 32)
    private String password;

    @Comment("昵称")
    @Column(name = "user_nickname", length = 30)
    private String nickname;

    @Comment("用户状态，0-禁用，1-普通用户，2-管理员")
    @Column(name = "user_state", nullable = false)
    private Integer state;


}