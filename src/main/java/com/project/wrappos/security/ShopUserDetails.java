package com.project.wrappos.security;

import com.project.wrappos.entity.Shop;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class ShopUserDetails implements UserDetails {

    private final Long shopNo;
    private final String userId;
    private final String userPw;
    private final String bizName;

    public ShopUserDetails(Shop shop) {
        this.shopNo = shop.getShopNo();
        this.userId = shop.getUserId();
        this.userPw = shop.getUserPw();
        this.bizName = shop.getBizName();
    }

    public Long getShopNo() {
        return shopNo;
    }

    public String getUserId() {
        return userId;
    }

    public String getBizName() {
        return bizName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}