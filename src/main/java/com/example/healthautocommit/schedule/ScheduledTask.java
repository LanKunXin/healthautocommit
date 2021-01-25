package com.example.healthautocommit.schedule;

import ch.qos.logback.classic.Logger;
import com.example.healthautocommit.entity.User;
import com.example.healthautocommit.service.UserService;
import com.example.healthautocommit.util.SendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ScheduledTask {
    @Autowired
    private UserService userService;
    @Autowired
    private SendRequest sendRequest;
    @Scheduled(cron = "* 1 0 * * ?")
    public void commit() {
        List<User> users = userService.listUser();
       /* Logger logger = Logger.;
        logger.info();*/
        for (User user : users) {
            sendRequest.sendMsg(user);
        }
    }
}
