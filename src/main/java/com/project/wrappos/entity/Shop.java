package com.project.wrappos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
@Getter
@Setter
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_no")
    private Long shopNo;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "user_pw", nullable = false, length = 255)
    private String userPw;

    @Column(name = "biz_no", nullable = false, unique = true, length = 10)
    private String bizNo;

    @Column(name = "biz_name", nullable = false, length = 100)
    private String bizName;

    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;

    @Column(name = "owner_tel", length = 20)
    private String ownerTel;

    @Column(name = "open_dt")
    private LocalDate openDt;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "shop_img", length = 255)
    private String shopImg;

    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

    @PrePersist
    public void prePersist() {
        if (this.regDt == null) {
            this.regDt = LocalDateTime.now();
        }
    }
}