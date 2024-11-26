package com.achobeta.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.util.StrUtil;
import com.achobeta.intercepter.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 所有的请求都加上 Log 拦截器
        registry.addInterceptor(new LogInterceptor());
    }

    @Bean
    public Converter<String, LocalDateTime> stringLocalDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (StrUtil.isAllCharMatch(source, new Matcher<Character>() {
                    @Override
                    public boolean match(Character c) {
                        return Character.isDigit(c); // 检查字符是否为数字
                    }
                })) {
                    return LocalDateTimeUtil.of(Long.parseLong(source));
                } else {
                    return DateUtil.parseLocalDateTime(source);
                }
            }
        };

    }

}
