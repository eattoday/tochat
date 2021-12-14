package com.dawu.tochat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawu.tochat.service.TestService;
import com.dawu.tochat.util.OkHttp3Util;
import com.dawu.tochat.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * 创建于   : guohaotian
 * 创建时间 : 2021/11/26 0026 09:53
 * 详细描述 :
 */
@Api(tags = "测试模块")
@Controller()
@RequestMapping("/test")
@Slf4j
public class TestController {


    @Autowired
    private TestService testService;

    @ResponseBody
    @PostMapping("/test")
    @ApiOperation(value = "动态测试,随时变更")
    public String test() {
        try {


            RedisUtils.setCacheObject("testString", "test1");
            String testString = RedisUtils.getCacheObject("testString");  // testString = test1

            Map map=new HashMap();
            map.put("test1","11");
            map.put("test2","22");
            RedisUtils.setCacheMap("testMap", map);
            Map testMap = RedisUtils.getCacheMap("testMap");    // testMap = {"test2":"22","test1":"11"}

            RedisUtils.setCacheList("testList", Arrays.asList("111","222"));
            List testList = RedisUtils.getCacheList("testList");    // testList = ["111","222"]

            Set set=new HashSet();
            set.add("111");
            set.add("222");
            RedisUtils.setCacheSet("testSet", set);
            Set testSet = RedisUtils.getCacheSet("testSet");    // testSet = ["222","111"]

        } catch (Exception e) {
            e.printStackTrace();
            return "请求报错";
        }
        return "请求成功";
    }

    @ResponseBody
    @GetMapping("/test1")
    @ApiOperation(value = "测试Mybatis")
    @ApiImplicitParam(name = "tableName", value = "表名", required = true)
    public String testMybatis(String tableName) {
        return testService.testMybatis(tableName);
    }


    @ResponseBody
    @GetMapping("/test3")
    public void test2(String tableName) {


    }


}
