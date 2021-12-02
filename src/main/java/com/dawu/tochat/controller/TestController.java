package com.dawu.tochat.controller;

import com.dawu.tochat.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private TestService testService;

    @ResponseBody
    @GetMapping("/test")
    @ApiOperation(value = "动态测试,随时变更")
    public String test() {
        try {
            System.out.println("测试成功!");
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
    @GetMapping("/test2")
    @ApiOperation(value = "动态测试,随时变更")
    public String testLog() {
        try {
            //日志的级别
            //从低到高
            //可以调整输出的日志级别；日志就只会在这个级别以后的高级别生效
            log.trace("这是trace日志");
            log.debug("这是debug信息");
            //SpringBoot默认给的是info级别，如果没指定就是默认的root级别
            log.info("这是info日志");
            log.warn("这是warn信息");
            log.error("这是Error信息");
        } catch (Exception e) {
            e.printStackTrace();
            return "请求报错";
        }
        return "请求成功";
    }

    @ResponseBody
    @GetMapping("/test3")
    public void test2(String tableName) {


    }

}
