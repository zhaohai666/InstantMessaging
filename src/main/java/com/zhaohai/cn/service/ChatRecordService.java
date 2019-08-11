package com.zhaohai.cn.service;

import com.zhaohai.cn.entity.TbChatRecord;

import java.util.List;

public interface ChatRecordService {
    void insert(TbChatRecord chatRecord);

    List<TbChatRecord> findByUserIdAndFriendId(String userid, String friendid);

    List<TbChatRecord> findUnreadByUserid(String userid);

    void updateStatusHasRead(String id);
}
