package com.achobeta.domain.render.model.valobj;

import lombok.*;

/**
 * @author chensongmin
 * @description 渲染文本结果值对象
 * @date 2024/11/4
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenderBookVO {

    private String userName;
    private String bookName;
    private String bookContent;

}
