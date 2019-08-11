package com.zhaohai.cn.service.impl;

import com.zhaohai.cn.entity.TbChatRecord;
import com.zhaohai.cn.entity.TbChatRecordExample;
import com.zhaohai.cn.entity.TbFriendReqExample;
import com.zhaohai.cn.mapper.TbChatRecordMapper;
import com.zhaohai.cn.service.ChatRecordService;
import com.zhaohai.cn.utils.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ChatRecordServiceImpl implements ChatRecordService {

    @Resource
    private TbChatRecordMapper chatRecordMapper;
    @Resource
    private IdWorker idWorker;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(TbChatRecord chatRecord) {
        chatRecord.setId(idWorker.nextId());
        chatRecord.setHasRead(0);
        chatRecord.setCreatetime(new Date());
        chatRecord.setHasDelete(0);
        chatRecordMapper.insertSelective(chatRecord);
    }

    @Override
    public List<TbChatRecord> findByUserIdAndFriendId(String userid, String friendid) {
        TbChatRecordExample example = new TbChatRecordExample();
        TbChatRecordExample.Criteria criteria1 = example.createCriteria();
        TbChatRecordExample.Criteria criteria2 = example.createCriteria();
        criteria1.andUseridEqualTo(userid);
        criteria1.andFriendidEqualTo(friendid);
        criteria1.andHasDeleteEqualTo(0);

        criteria2.andUseridEqualTo(userid);
        criteria2.andFriendidEqualTo(friendid);
        criteria2.andHasDeleteEqualTo(0);
        example.or(criteria1);
        example.or(criteria2);

        // 将发给userid的所有聊天记录设置为已读
        TbChatRecordExample tbChatRecordExample = new TbChatRecordExample();
        tbChatRecordExample.createCriteria().andFriendidEqualTo(userid);
        tbChatRecordExample.createCriteria().andHasReadEqualTo(0);

        List<TbChatRecord> tbChatRecords1 = chatRecordMapper.selectByExample(tbChatRecordExample);
        for (TbChatRecord tbChatRecord : tbChatRecords1) {
            tbChatRecord.setHasRead(1);
            chatRecordMapper.updateByPrimaryKeySelective(tbChatRecord);
        }

        List<TbChatRecord> tbChatRecords = chatRecordMapper.selectByExample(example);
        return tbChatRecords;
    }

    @Override
    public List<TbChatRecord> findUnreadByUserid(String userid) {
        TbChatRecordExample tbChatRecordExample = new TbChatRecordExample();
        tbChatRecordExample.createCriteria().andFriendidEqualTo(userid);
        tbChatRecordExample.createCriteria().andHasReadEqualTo(0);
        return chatRecordMapper.selectByExample(tbChatRecordExample);
    }

    @Override
    public void updateStatusHasRead(String id) {
        TbChatRecord tbChatRecord = chatRecordMapper.selectByPrimaryKey(id);
        tbChatRecord.setHasRead(1);
        chatRecordMapper.updateByPrimaryKeySelective(tbChatRecord);
    }
}
