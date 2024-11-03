package com.achobeta.read;

import org.springframework.stereotype.Repository;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
@Repository
public class TextDAO {
    private String content = "默认值";

    public String getTextById(String textId) {
        return content;
    }
}
