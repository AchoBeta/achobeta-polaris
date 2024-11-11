package com.achobeta.types.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Constants {

    public final static String SPLIT = ",";

    public final static String TRACE_ID = "traceId";

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ResponseCode {
        SUCCESS("0000", "调用成功"),
        UN_ERROR("0001", "调用失败"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        NO_LOGIN("0003", "未登录"),

        // 鉴权系统错误以 1xxx 开始
        NO_PERMISSIONS("1001", "无权限访问")
        ;

        private String code;
        private String info;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum BizModule {
        RENDER("biz_001", "文本渲染模块"),
        DEVICE("biz_002","设备渲染模块"),
        ANNOUNCE("biz_003","公告渲染模块")
        ;
        private String code;
        private String name;
    }

}
