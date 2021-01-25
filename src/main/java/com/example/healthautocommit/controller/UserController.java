package com.example.healthautocommit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.healthautocommit.entity.User;
import com.example.healthautocommit.service.UserService;
import com.example.healthautocommit.util.R;
import com.example.healthautocommit.util.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LanKunXin
 * @since 2020-11-12
 */
@RestController
@RequestMapping("/health/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/uploadUser")
    public R uploadUser(@RequestBody User user) {
        userService.uploadUser(user);
        return R.ok();
    }

    @GetMapping("listUser")
    public R listUser() {
        List<User> users = userService.listUser();
        return R.ok().data("list", users);
    }
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        if (! StringUtils.isEmpty(user.getCardNo()) && ! StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(SHA256Util.getSHA256StrJava(user.getPassword()));
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("card_no", user.getCardNo()).eq("password", user.getPassword());
            user = userService.getOne(queryWrapper);
            if (user != null) {
                return R.ok().data("userInfo", user);
            }
        }
        return R.error().data("info", "账号密码错误");
    }
}

