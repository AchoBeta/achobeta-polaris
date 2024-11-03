package com.achobeta.read.bad_design;

import com.achobeta.read.TextDAO;
import com.achobeta.read.PatternStrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chensongmin
 * @description
 * <p>需求1：展示数据库里的文本</p>
 * <p>需求2：如果文本中含有 AchoBeta Polaris 或 AchoBeta x.0, 需要进行<b>加粗</b>处理</p>
 * @create 2024/11/3
 */
@Service
@RequiredArgsConstructor
public class ReadService2 {

    public static final String REPLACE_VAR_REGEX = "(AchoBeta\\s+\\d+\\.\\d+|AchoBeta\\s+Polaris)";
    private final TextDAO textDAO;

    public String read(String textId) {
        String text = textDAO.getTextById(textId);
        return PatternStrUtil.replaceText(text, "<br>", "</br>");
    }

}
