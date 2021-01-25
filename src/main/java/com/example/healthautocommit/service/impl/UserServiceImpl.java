package com.example.healthautocommit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.healthautocommit.entity.User;
import com.example.healthautocommit.mapper.UserMapper;
import com.example.healthautocommit.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthautocommit.util.SHA256Util;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LanKunXin
 * @since 2020-11-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void uploadUser(User user) {
        if (user != null) {
            // 校验数据库中是否有这个用户
            if (!StringUtils.isEmpty(user.getPassword())
                    && !StringUtils.isEmpty(user.getCardNo())) {
                String secretPwd = SHA256Util.getSHA256StrJava(user.getPassword());
                user.setPassword(secretPwd);
                User people = baseMapper.selectOne(new QueryWrapper<User>().eq("card_no", user.getCardNo()));
                if (people == null) {
                    baseMapper.insert(user);
                } else {
                    baseMapper.update(user, new QueryWrapper<User>().eq("cardNo", user.getCardNo()));
                }
            }
        }
    }

    @Override
    public List<User> listUser() {
        return baseMapper.selectList(null);
    }


}
