package com.project.wrappos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignupRequestDto {

    private String userId;
    private String userPw;
    private String userPwConfirm;
    private String bizNo;
    private String bizName;
    private String ownerName;
    private String ownerTel;
    private LocalDate openDt;
    private String address;
}