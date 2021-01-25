package com.example.healthautocommit.service;

import com.example.healthautocommit.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LanKunXin
 * @since 2020-11-12
 */
public interface UserService extends IService<User> {

    void uploadUser(User user);

    List<User> listUser();
}
