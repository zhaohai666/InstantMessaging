package com.zhaohai.cn.service;

import com.zhaohai.cn.entity.TbUser;
import com.zhaohai.cn.entity.vo.User;

import java.util.List;

public interface UserService {

    List<TbUser> getUsers();

    User login(String username, String password);

    User register(TbUser user);

    void updateNickname(String id, String nickname);

    User findById(String userId);

    User findByUserName(String userId, String friendUserName);
}
