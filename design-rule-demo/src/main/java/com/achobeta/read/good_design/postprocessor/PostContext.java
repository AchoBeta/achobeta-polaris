package com.achobeta.read.good_design.postprocessor;

import lombok.*;

/**
 * @author chensongmin
 * @description Post扩展点数据上下文
 * 用于在主流程与分支流程的数据传递
 * @create 2024/11/3
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostContext<T> {

    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 业务数据
     */
    private T bizData;

}
