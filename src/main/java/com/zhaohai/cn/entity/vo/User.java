package com.zhaohai.cn.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private String id;

    private String username;

    private String picSmall;

    private String picNormal;

    private String nickname;

    private String qrcode;

    private String clientId;

    private String sign;

    private Date createtime;

    private String phone;

}
