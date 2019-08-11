package com.zhaohai.cn.service.impl;

import com.zhaohai.cn.entity.*;
import com.zhaohai.cn.entity.vo.FriendReq;
import com.zhaohai.cn.entity.vo.User;
import com.zhaohai.cn.mapper.TbFriendMapper;
import com.zhaohai.cn.mapper.TbFriendReqMapper;
import com.zhaohai.cn.mapper.TbUserMapper;
import com.zhaohai.cn.service.FriendService;
import com.zhaohai.cn.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    private TbFriendReqMapper tbFriendReqMapper;
    @Resource
    private TbFriendMapper tbFriendMapper;
    @Resource
    private TbUserMapper tbUserMapper;
    @Resource
    private IdWorker idWorker;
    @Resource
    private UserServiceImpl userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User sendRequest(String fromUserid, String toUserid) {
        TbUser tbUser = tbUserMapper.selectByPrimaryKey(toUserid);
        User user = null;
        try {
            user = userService.getUser(fromUserid, tbUser);
        } catch (Exception e) {
            log.error("sendRequest error : {}", e);
        }
        if (user == null) {
            return null;
        }
        TbFriendReq tbFriendReq = new TbFriendReq();
        tbFriendReq.setId(idWorker.nextId());
        tbFriendReq.setFromUserid(fromUserid);
        tbFriendReq.setToUserid(toUserid);
        tbFriendReq.setCreatetime(new Date());
        tbFriendReq.setStatus(0);
        tbFriendReqMapper.insert(tbFriendReq);
        return new User();
    }

    @Override
    public List<FriendReq> findFriendReqByUserId(String userId) {
        TbFriendReqExample tbFriendReqExample = new TbFriendReqExample();
        tbFriendReqExample.createCriteria().andToUseridEqualTo(userId);
        tbFriendReqExample.createCriteria().andStatusEqualTo(0);
        List<TbFriendReq> tbFriendReqs = tbFriendReqMapper.selectByExample(tbFriendReqExample);
        List<FriendReq> friendUserList = new ArrayList<>(tbFriendReqs.size());
        for (TbFriendReq friendReq : tbFriendReqs) {
            TbUser tbUser = tbUserMapper.selectByPrimaryKey(friendReq.getFromUserid());
            FriendReq user = new FriendReq();
            BeanUtils.copyProperties(tbUser, user);
            user.setId(friendReq.getId());
            friendUserList.add(user);
        }
        return friendUserList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptFriendReq(String reqid) {
        TbFriendReq tbFriendReq = tbFriendReqMapper.selectByPrimaryKey(reqid);
        tbFriendReq.setStatus(1);
        tbFriendReqMapper.updateByPrimaryKey(tbFriendReq);

        TbFriend friend1 = new TbFriend();
        friend1.setId(idWorker.nextId());
        friend1.setUserid(tbFriendReq.getFromUserid());
        friend1.setFriendsId(tbFriendReq.getToUserid());
        friend1.setCreatetime(new Date());

        TbFriend friend2 = new TbFriend();
        friend2.setId(idWorker.nextId());
        friend2.setUserid(tbFriendReq.getToUserid());
        friend2.setFriendsId(tbFriendReq.getFromUserid());
        friend2.setCreatetime(new Date());
        tbFriendMapper.insertSelective(friend1);
        tbFriendMapper.insertSelective(friend2);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void ignoreFriendReq(String reqid) {
        TbFriendReq friendReq = tbFriendReqMapper.selectByPrimaryKey(reqid);
        friendReq.setStatus(1);
        tbFriendReqMapper.updateByPrimaryKey(friendReq);
    }

    @Override
    public List<User> findFriendByUserid(String userid) {
        TbFriendExample friendExample = new TbFriendExample();
        friendExample.createCriteria().andUseridEqualTo(userid);
        List<TbFriend> tbFriends = tbFriendMapper.selectByExample(friendExample);
        List<User> users = new ArrayList<>(tbFriends.size());
        tbFriends.forEach(tbFriend -> {
            TbUser tbUser = tbUserMapper.selectByPrimaryKey(tbFriend.getFriendsId());
            User user = new User();
            BeanUtils.copyProperties(tbUser, user);
            users.add(user);
        });
        return users;
    }
}
