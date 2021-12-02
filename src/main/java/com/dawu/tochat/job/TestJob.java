package com.dawu.tochat.job;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@Slf4j
@Profile("prod")
public class TestJob {

    // 定时每分钟提醒提醒
    @Scheduled(cron = "0 * * * * ?")
    private void testJobByCron() {
        log.trace("开始任务 : " + LocalDateTime.now());

        log.trace("定时任务时间为 : " + LocalDateTime.now());

        log.trace("结束任务 : " + LocalDateTime.now());
    }

    // 直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate = 5000)
    private void testJobByFixedRate() {
        log.trace("开始任务 : " + LocalDateTime.now());

        log.trace("间隔5秒任务 : " + LocalDateTime.now());

        log.trace("结束任务 : " + LocalDateTime.now());
    }

}
