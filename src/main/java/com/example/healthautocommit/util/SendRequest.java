package com.example.healthautocommit.util;

import com.example.healthautocommit.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendRequest {
    public void sendMsg(User user) {
        try {
            String cardNo = user.getCardNo();
            String password = user.getPassword();

            RestTemplate restTemplate = new RestTemplate();
            URI uri = null;
            uri = new URI("http://hmgr.sec.lit.edu.cn/wms/healthyLogin");

            // 封装登录请求参数
            Map<String, String> map = new HashMap<>();
            map.put("cardNo", cardNo);
            map.put("password", password);

            // 发送post请求，返回map
            Map resultMap = restTemplate.postForObject(uri, map, Map.class);
            // 获取响应数据
            Map data = (Map) resultMap.get("data");
            String token = (String) data.get("token");

            String address = "http://hmgr.sec.lit.edu.cn/wms/lastHealthyRecord?teamId="+data.get("teamId")+"&userId="+data.get("userId");
            uri = new URI(address);
            HttpHeaders headers = new HttpHeaders();
            headers.add("token", token);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

            // 获取请求中的参数
            Map body = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Map.class).getBody();
            Map yesterdayMap = (Map) body.get("data");
            yesterdayMap.remove("temperatureTwo");
            yesterdayMap.remove("temperatureThree");
            yesterdayMap.put("temperatureTwo", "");
            yesterdayMap.put("temperatureThree", "");

            yesterdayMap.remove("createTime");
            yesterdayMap.remove("id");
            yesterdayMap.remove("reportDate");

            yesterdayMap.put("isTrip", "0");
        /*yesterdayMap.put("tripList", "");
        yesterdayMap.put("peerList", "");*/
            yesterdayMap.put("mobile", data.get("mobile"));
            httpEntity = new HttpEntity<>(yesterdayMap, headers);
            uri = new URI("http://hmgr.sec.lit.edu.cn/wms/addHealthyRecord");
            Map result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Map.class).getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
