package com.project.wrappos.controller;

import com.project.wrappos.dto.SignupRequestDto;
import com.project.wrappos.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/signup/check-user-id")
    @ResponseBody
    public Map<String, Object> checkUserId(@RequestParam("userId") String userId) {
        boolean duplicated = shopService.existsByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("duplicated", duplicated);
        return result;
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequestDto dto) {
        Map<String, String> errors = shopService.validateSignup(dto);

        if (!errors.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("fieldErrors", errors);
            result.put("message", "입력값을 확인해주세요.");
            return ResponseEntity.badRequest().body(result);
        }

        try {
            shopService.signup(dto);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "회원가입이 완료되었습니다.");
            return ResponseEntity.ok(result);

        } catch (DataIntegrityViolationException e) {
            Map<String, String> fieldErrors = new HashMap<>();

            String errorMessage = e.getMostSpecificCause() != null
                    ? e.getMostSpecificCause().getMessage()
                    : e.getMessage();

            if (errorMessage != null) {
                String lowerMessage = errorMessage.toLowerCase();

                if (lowerMessage.contains("uk_shop_user_id") || lowerMessage.contains("user_id")) {
                    fieldErrors.put("userId", "이미 사용 중인 아이디입니다.");
                }

                if (lowerMessage.contains("uk_shop_biz_no") || lowerMessage.contains("biz_no")) {
                    fieldErrors.put("bizNo", "이미 등록된 사업자등록번호입니다.");
                }
            }

            if (fieldErrors.isEmpty()) {
                fieldErrors.put("common", "중복된 데이터가 존재합니다. 입력값을 다시 확인해주세요.");
            }

            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("fieldErrors", fieldErrors);
            result.put("message", "저장 중 중복 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(result);

        } catch (Exception e) {
            e.printStackTrace();

            Map<String, String> fieldErrors = new HashMap<>();
            fieldErrors.put("common", "회원가입 처리 중 오류가 발생했습니다.");

            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("fieldErrors", fieldErrors);
            result.put("message", "회원가입 처리 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
    }
}