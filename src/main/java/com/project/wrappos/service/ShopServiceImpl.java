package com.project.wrappos.service;

import com.project.wrappos.dto.SignupRequestDto;
import com.project.wrappos.entity.Shop;
import com.project.wrappos.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> validateSignup(SignupRequestDto dto) {
        Map<String, String> errors = new HashMap<>();

        String userId = safeTrim(dto.getUserId());
        String userPw = safeTrim(dto.getUserPw());
        String userPwConfirm = safeTrim(dto.getUserPwConfirm());
        String bizNo = onlyNumber(dto.getBizNo());
        String bizName = safeTrim(dto.getBizName());
        String ownerName = safeTrim(dto.getOwnerName());

        if (userId.isEmpty()) {
            errors.put("userId", "아이디를 입력하세요.");
        } else if (shopRepository.existsByUserId(userId)) {
            errors.put("userId", "이미 사용 중인 아이디입니다.");
        }

        if (userPw.isEmpty()) {
            errors.put("userPw", "비밀번호를 입력하세요.");
        }

        if (userPwConfirm.isEmpty()) {
            errors.put("userPwConfirm", "비밀번호 확인을 입력하세요.");
        } else if (!userPw.isEmpty() && !userPw.equals(userPwConfirm)) {
            errors.put("userPwConfirm", "비밀번호가 일치하지 않습니다.");
        }

        if (bizNo.isEmpty()) {
            errors.put("bizNo", "사업자등록번호를 입력하세요.");
        } else if (bizNo.length() != 10) {
            errors.put("bizNo", "사업자등록번호는 숫자 10자리여야 합니다.");
        } else if (shopRepository.existsByBizNo(bizNo)) {
            errors.put("bizNo", "이미 등록된 사업자등록번호입니다.");
        }

        if (bizName.isEmpty()) {
            errors.put("bizName", "상호를 입력하세요.");
        }

        if (ownerName.isEmpty()) {
            errors.put("ownerName", "대표자명을 입력하세요.");
        }

        return errors;
    }

    @Override
    public void signup(SignupRequestDto dto) {
        Shop shop = new Shop();
        shop.setUserId(safeTrim(dto.getUserId()));
        shop.setUserPw(passwordEncoder.encode(dto.getUserPw()));
        shop.setBizNo(onlyNumber(dto.getBizNo()));
        shop.setBizName(safeTrim(dto.getBizName()));
        shop.setOwnerName(safeTrim(dto.getOwnerName()));
        shop.setOwnerTel(safeTrim(dto.getOwnerTel()));
        shop.setOpenDt(dto.getOpenDt());
        shop.setAddress(safeTrim(dto.getAddress()));

        shopRepository.save(shop);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserId(String userId) {
        return !safeTrim(userId).isEmpty() && shopRepository.existsByUserId(safeTrim(userId));
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private String onlyNumber(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }
}