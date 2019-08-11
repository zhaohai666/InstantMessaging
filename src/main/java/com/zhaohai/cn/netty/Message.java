package com.zhaohai.cn.netty;

import com.zhaohai.cn.entity.TbChatRecord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    /**
     * 消息类型
     */
    private String type;

    /**
     * 聊天消息
     */
    private TbChatRecord chatRecord;

    /**
     * 扩展消息字段
     */
    private Object ext;
}
