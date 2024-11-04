package com.achobeta.test;

import com.achobeta.read.TextDAO;
import com.achobeta.read.UserInfo;
import com.achobeta.read.bad_design.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author chensongmin
 * @description 设计原则 Bad Design 测试类
 * @create 2024/11/3
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class ReadServiceTest {

    @Resource
    private TextDAO textDAO;
    @Resource
    private ReadService1 readService1;
    @Resource
    private ReadService2 readService2;
    @Resource
    private ReadService3 readService3;
    @Resource
    private ReadService4 readService4;
    @Resource
    private ReadService5 readService5;

    @Test
    void read1() {
        ReflectionTestUtils.setField(textDAO, "content", "这是 AchoBeta Polaris 北极星系统");
        log.info("readService1.renderText(\"10001\") = " + readService1.read("10001"));
    }

    @Test
    void read2() {
        ReflectionTestUtils.setField(textDAO, "content", "这是 AchoBeta Polaris 北极星系统, AchoBeta 6.0 Java 组复试项目");
        log.info("readService2.renderText(\"10001\") = " + readService2.read("10001"));
    }

    @Test
    void read3() {
        ReflectionTestUtils.setField(textDAO, "content", "这是 AchoBeta Polaris 北极星系统, AchoBeta 6.0 Java 组复试项目");
        UserInfo userInfoA = UserInfo.builder()
                .name("AchoBeta 5.0")
                .isAuth(true).build();
        log.info("readService3.renderText(" + userInfoA + ", \"10001\") = " + readService3.read(userInfoA, "10001"));

        UserInfo userInfoB = UserInfo.builder()
                .name("AchoBeta 7.0")
                .isAuth(false).build();
        log.info("readService3.renderText(" + userInfoB + ", \"10001\") = " + readService3.read(userInfoB, "10001"));
    }

    @Test
    void read4() {
        ReflectionTestUtils.setField(textDAO, "content", "这是 AchoBeta Polaris 北极星系统, AchoBeta 6.0 Java 组复试项目");
        UserInfo userInfoA = UserInfo.builder()
                .name("AchoBeta 5.0")
                .isAuth(true).build();
        log.info("readService4.renderText(" + userInfoA + ", \"10001\") = " + readService4.read(userInfoA, "10001"));

        UserInfo userInfoB = UserInfo.builder()
                .name("AchoBeta 6.0")
                .isAuth(true).build();
        log.info("readService4.renderText(" + userInfoB + ", \"10001\") = " + readService4.read(userInfoB, "10001"));
    }

    @Test
    void read5() {
        ReflectionTestUtils.setField(textDAO, "content", "这是 AchoBeta Polaris 北极星系统, AchoBeta 6.0 Java 组复试项目");
        UserInfo userInfo = UserInfo.builder()
                .name("【Java萌新】半糖同学")
                .isAuth(false).build();
        log.info("readService5.renderText(" + userInfo + ", \"10001\") = " + readService5.read(userInfo, "10001"));
    }
    
}