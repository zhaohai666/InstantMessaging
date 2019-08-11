package com.zhaohai.cn.controller;

import com.zhaohai.cn.entity.TbChatRecord;
import com.zhaohai.cn.service.ChatRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/chatrecord")
public class ChatrecordController {

    @Resource
    private ChatRecordService chatRecordService;

    @GetMapping("/findByUserIdAndFriendId")
    public List<TbChatRecord> findByUserIdAndFriendId(String userid, String friendid) {
        return chatRecordService.findByUserIdAndFriendId(userid, friendid);
    }

    @GetMapping("/findUnreadByUserid")
    public List<TbChatRecord> findUnreadByUserid(String userid) {
        return chatRecordService.findUnreadByUserid(userid);
    }
}
