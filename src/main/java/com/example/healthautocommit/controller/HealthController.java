package com.example.healthautocommit.controller;

import com.example.healthautocommit.util.SHA256Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Api(value = "健康上报控制器", description = "健康上报控制器")
@Controller
@RequestMapping("/health")
@CrossOrigin
public class HealthController {
    @ApiOperation("登录")
    @GetMapping("/login/{cardNo}/{password}")
    @ResponseBody
    public String login(@PathVariable String cardNo, @PathVariable String password) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        //cardNo
        //password
        RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
        uri = new URI("http://hmgr.sec.lit.edu.cn/wms/healthyLogin");

        // 封装登录请求参数
        password = SHA256Util.getSHA256StrJava(password);
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

        return null;
    }

    @GetMapping("/toLogin")

    public String toLogin() {
        return "login";
    }
}
