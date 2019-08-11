package com.zhaohai.cn.controller;

import com.zhaohai.cn.entity.TbFriendReq;
import com.zhaohai.cn.entity.vo.FriendReq;
import com.zhaohai.cn.entity.vo.Result;
import com.zhaohai.cn.entity.vo.User;
import com.zhaohai.cn.service.FriendService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Resource
    private FriendService friendService;

    @PostMapping("/sendRequest")
    public Result sendRequest(@RequestBody TbFriendReq tbFriendReq) {
        User user = friendService.sendRequest(tbFriendReq.getFromUserid(), tbFriendReq.getToUserid());
        if (user == null) {
            return new Result(false, "申请好友失败");
        }
        return new Result(true, "申请好友成功");
    }

    @GetMapping("/findFriendReqByUserid")
    public List<FriendReq> findFriendReqByUserId(@RequestParam("userid") String userid) {
        return friendService.findFriendReqByUserId(userid);
    }

    @GetMapping("/acceptFriendReq")
    public Result acceptFriendReq(String reqid) {
        friendService.acceptFriendReq(reqid);
        return new Result(true, "操作成功");
    }

    @GetMapping("/ignoreFriendReq")
    public Result ignoreFriendReq(String reqid) {
        friendService.ignoreFriendReq(reqid);
        return new Result(true, "忽略添加好友成功");
    }

    @GetMapping("/findFriendByUserid")
    public List<User> findFriendByUserid(String userid) {
        return friendService.findFriendByUserid(userid);
    }

}
