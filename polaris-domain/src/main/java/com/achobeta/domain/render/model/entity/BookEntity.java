package com.achobeta.domain.render.model.entity;

import lombok.*;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    /**
     * 文本业务ID
     */
    private String bookId;
    /**
     * 文本名称
     */
    private String bookName;
    /**
     * 文本内容
     */
    private String bookContent;

}
