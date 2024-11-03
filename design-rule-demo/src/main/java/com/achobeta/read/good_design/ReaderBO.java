package com.achobeta.read.good_design;

import com.achobeta.read.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chensongmin
 * @description 渲染文本承载类
 * @create 2024/11/3
 */
@Getter
@Setter
@Builder
@ToString
public class ReaderBO {

    private UserInfo user;
    private String textId;
    private String text;

}
