package com.zhaohai.cn.netty;


import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 建立用户ID与管道的关联
 */
public class UserChannelMap {
    /**
     * 用于保存用户id与管道的Map对象
     */
    private static Map<String, Channel> userChannelMap;

    static {
        userChannelMap = new HashMap<>();
    }

    /**
     * 添加用户id与channel的关联
     *
     * @param userid
     * @param channel
     */
    public static void put(String userid, Channel channel) {
        userChannelMap.put(userid, channel);
    }

    /**
     * 移除用户id与channel的关联
     *
     * @param userid
     */
    public static void remove(String userid) {
        userChannelMap.remove(userid);
    }

    public static void removeByChannelId(String channelId) {
        if (StringUtils.isEmpty(channelId)) {
            return;
        }
        for (String key : userChannelMap.keySet()) {
            Channel channel = userChannelMap.get(key);
            if (channelId.equals(channel.id().asLongText())) {
                System.out.println("客户端连接断开，取消用户id ：" + key + "与管道 ：" + channelId + "的关联");
                userChannelMap.remove(key);
                break;
            }
        }
    }

    /**
     * 打印所有用户与通道的关联数据
     */
    public static void print() {
        for (String s : userChannelMap.keySet()) {
            System.out.println("用户id ：" + s + " 通道 ： " + userChannelMap.get(s).id());
        }
    }

    /**
     * 根据好友id获取对于的通道
     *
     * @param friendid ：好友id
     * @return ：通道
     */
    public static Channel get(String friendid) {
        return userChannelMap.get(friendid);
    }
}
