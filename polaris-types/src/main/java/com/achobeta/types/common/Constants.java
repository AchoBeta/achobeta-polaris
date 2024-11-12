package com.achobeta.types.common;

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


}
