package com.project.wrappos.service;

import com.project.wrappos.dto.SignupRequestDto;

import java.util.Map;

public interface ShopService {

    Map<String, String> validateSignup(SignupRequestDto dto);

    void signup(SignupRequestDto dto);

    boolean existsByUserId(String userId);
}