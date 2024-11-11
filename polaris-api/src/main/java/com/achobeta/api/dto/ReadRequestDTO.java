package com.achobeta.api.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
@Getter
@Setter
public class ReadRequestDTO implements Serializable {

    private String name;
    /**
     * <a href=https://blog.csdn.net/u012475575/article/details/85260372>
     *     如果布尔类型是 boolean, 不要以 is 前缀开头，不然传不过去</a>
     *     <br>
     * <a href=https://www.cnblogs.com/yemaxu/p/4453090.html>
     *     如果是 Boolean 类型就没问题</a>
     */
    private Boolean isAuth;
    private String textId;

}
