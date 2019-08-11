package com.zhaohai.cn.service.impl;

import com.zhaohai.cn.entity.*;
import com.zhaohai.cn.entity.vo.User;
import com.zhaohai.cn.mapper.TbFriendMapper;
import com.zhaohai.cn.mapper.TbFriendReqMapper;
import com.zhaohai.cn.mapper.TbUserMapper;
import com.zhaohai.cn.service.UserService;
import com.zhaohai.cn.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TbUserMapper tbUserMapper;

    @Resource
    private IdWorker idWorker;
    @Resource
    private TbFriendMapper tbFriendMapper;
    @Resource
    private TbFriendReqMapper tbFriendReqMapper;

    @Override
    public List<TbUser> getUsers() {
        return tbUserMapper.selectByExample(null);
    }

    @Override
    public User login(String username, String password) {
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            TbUserExample tbUserExample = new TbUserExample();
            tbUserExample.createCriteria().andUsernameEqualTo(username);
            tbUserExample.createCriteria().andPasswordEqualTo(password);
            List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
            if (!CollectionUtils.isEmpty(tbUsers)) {
                TbUser tbUser = tbUsers.get(0);
                String encodingPassword = DigestUtils.md5DigestAsHex(password.getBytes());
                if (encodingPassword.equals(tbUser.getPassword())) {
                    User user = new User();
                    BeanUtils.copyProperties(tbUser, user);
                    return user;
                }
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User register(TbUser user) {
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (!CollectionUtils.isEmpty(tbUsers)) {
            return null;
        }
        user.setId(idWorker.nextId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setPicNormal("");
        user.setPicSmall("");
        user.setNickname(user.getUsername());
        user.setQrcode("");
        user.setCreatetime(new Date());
        tbUserMapper.insert(user);
        return new User();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNickname(String id, String nickname) {
        if (StringUtils.isNotEmpty(nickname)) {
            TbUser tbUser = tbUserMapper.selectByPrimaryKey(id);
            tbUser.setNickname(nickname);
            tbUserMapper.updateByPrimaryKey(tbUser);
        }
    }

    @Override
    public User findById(String userId) {
        TbUser user = tbUserMapper.selectByPrimaryKey(userId);
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        return user1;
    }

    @Override
    public User findByUserName(String userId, String friendUserName) {
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andUsernameEqualTo(friendUserName);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        for (TbUser tbUser : tbUsers) {
            if (userId.equals(tbUser.getId())) {
                return null;
            }
        }
        if (CollectionUtils.isEmpty(tbUsers)) {
            return null;
        }
        TbUser user = tbUsers.get(0);
        try {
            return getUser(userId, user);
        } catch (Exception e) {
            log.error("findByUserName error : {}", e);
        }
        return null;
    }

    User getUser(String userId, TbUser user) throws Exception{
        Future<List<TbFriend>> tbFriends = getTbFriends(userId, user);
        if (!CollectionUtils.isEmpty(tbFriends.get())) {
            return null;
        }
        Future<List<TbFriendReq>> tbFriendReqs = getTbFriendReqs(userId, user);
        if (!CollectionUtils.isEmpty(tbFriendReqs.get())) {
            return null;
        }
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        return user1;
    }

    @Async("mySimpleAsync")
    public Future<List<TbFriendReq>> getTbFriendReqs(String userId, TbUser user) {
        TbFriendReqExample tbFriendReqExample = new TbFriendReqExample();
        tbFriendReqExample.createCriteria().andFromUseridEqualTo(userId);
        tbFriendReqExample.createCriteria().andToUseridEqualTo(user.getId());
        tbFriendReqExample.createCriteria().andStatusEqualTo(0);
        return new AsyncResult<>(tbFriendReqMapper.selectByExample(tbFriendReqExample));
    }

    @Async("mySimpleAsync")
    public Future<List<TbFriend>> getTbFriends(String userId, TbUser user) {
        TbFriendExample tbFriendExample = new TbFriendExample();
        tbFriendExample.createCriteria().andUseridEqualTo(userId);
        tbFriendExample.createCriteria().andFriendsIdEqualTo(user.getId());
        return new AsyncResult<>(tbFriendMapper.selectByExample(tbFriendExample));
    }
}
