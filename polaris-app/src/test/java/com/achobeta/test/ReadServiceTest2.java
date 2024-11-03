package com.achobeta.test;

import com.achobeta.read.TextDAO;
import com.achobeta.read.UserInfo;
import com.achobeta.read.good_design.ReadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadServiceTest2 {

    @Resource
    private TextDAO textDAO;
    @Resource
    private ReadService readService;

    private UserInfo userInfo;
    private UserInfo userInfoA;
    private UserInfo userInfoB;
    private UserInfo userInfoC;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(textDAO, "content", "这是 AchoBeta Polaris 北极星系统, AchoBeta 6.0 Java 组复试项目");

        userInfo = UserInfo.builder().name("【Java萌新】半糖同学").isAuth(false).build();
        userInfoA = UserInfo.builder().name("AchoBeta 5.0").isAuth(true).build();
        userInfoB = UserInfo.builder().name("AchoBeta 6.0").isAuth(true).build();
        userInfoC = UserInfo.builder().name("AchoBeta 7.0").isAuth(false).build();
    }

    @Test
    public void testReadWhiteList() {
        log.info("readService.reader( " + userInfo + ", \"10001\") = " + readService.reader(userInfo, "10001"));
    }

    @Test
    public void testReadGroupShow() {
        log.info("readService.reader(" + userInfoA + ", \"10001\") = " + readService.reader(userInfoA, "10001"));
        log.info("readService.reader(" + userInfoB + ", \"10001\") = " + readService.reader(userInfoB, "10001"));
        log.info("readService.reader(" + userInfoC + ", \"10001\") = " + readService.reader(userInfoC, "10001"));
    }

    @Test
    public void testTemplate() {
        log.info("readService.readTemplate(" + userInfo + ", \"10001\") = " + readService.readTemplate(userInfoA, "10001"));

    }

}
