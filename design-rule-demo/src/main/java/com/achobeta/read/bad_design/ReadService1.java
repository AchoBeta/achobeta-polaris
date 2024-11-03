package com.achobeta.read.bad_design;

import com.achobeta.read.TextDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chensongmin
 * @description 需求：展示数据库里的文本
 * @create 2024/11/3
 */
@Service
@RequiredArgsConstructor
public class ReadService1 {

    private final TextDAO textDAO;

    public String read(String textId) {
        return textDAO.getTextById(textId);
    }

}
