package com.zhaohai.cn.service;

import com.zhaohai.cn.entity.vo.FriendReq;
import com.zhaohai.cn.entity.vo.User;

import java.util.List;

public interface FriendService {
    User sendRequest(String fromUserid, String toUserid);

    List<FriendReq> findFriendReqByUserId(String userid);

    void acceptFriendReq(String reqid);

    void ignoreFriendReq(String reqid);

    List<User> findFriendByUserid(String userid);
}
