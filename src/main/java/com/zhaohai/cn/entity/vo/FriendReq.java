package com.zhaohai.cn.entity.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FriendReq {

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
