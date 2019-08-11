package com.zhaohai.cn.controller;

import com.zhaohai.cn.entity.TbFriendReq;
import com.zhaohai.cn.entity.TbUser;
import com.zhaohai.cn.entity.vo.Result;
import com.zhaohai.cn.entity.vo.User;
import com.zhaohai.cn.service.UserService;
import com.zhaohai.cn.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/findALl")
    public List<TbUser> getUser() {
        List<TbUser> users = userService.getUsers();
        return users;
    }

    @PostMapping("/login")
    public Result login(@RequestBody TbUser tbUser) {
        User user = userService.login(tbUser.getUsername(), tbUser.getPassword());
        if (user == null) {
            return new Result(false, "登陆失败，请检查用户名和密码！");
        }
        return new Result(true, "登陆成功", user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody TbUser user) {
        User register = userService.register(user);
        if (register == null) {
            return new Result(false, "用户已经存在！");
        }
        return new Result(true, "注册成功！");
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        File imageFile = UploadUtils.getImageFile();
        log.info("file_path : {}", imageFile.getAbsolutePath());
        File newFile = null;
        try {
            newFile = new File(imageFile.getAbsolutePath() + File.separator + filename);
            log.info("newFile : {}", newFile.getAbsolutePath());
            file.transferTo(newFile);
        } catch (IOException e) {
            log.error("UserController | upload error : {}", e);
        }
        return new Result(true, "上传成功", newFile.getAbsolutePath());
    }

    @PostMapping("/updateNickname")
    public Result updateNickname(@RequestBody TbUser user) {
        userService.updateNickname(user.getId(), user.getNickname());
        return new Result(true, "修改成功");
    }

    @GetMapping("/findById")
    public User findById(@RequestParam("userId") String userId) {
        User user = userService.findById(userId);
        return user;
    }

    @GetMapping("findByUserName")
    public Result findByUserName(String userId, String friendUserName) {
        User user = userService.findByUserName(userId, friendUserName);
        if (user != null) {
            return new Result(true, "搜索成功", user);
        } else {
            return new Result(false, "没有找到该用户");
        }
    }

}