package com.zhaohai.cn.entity.vo;

import lombok.Data;

@Data
public class Result {
    private Boolean success;
    private String message;
    private Object result;

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(Boolean success, String message, Object result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }
}
