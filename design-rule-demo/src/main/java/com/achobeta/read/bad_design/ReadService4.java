package com.achobeta.read.bad_design;

import com.achobeta.read.AuthHolder;
import com.achobeta.read.TextDAO;
import com.achobeta.read.UserInfo;
import com.achobeta.read.PatternStrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chensongmin
 * @description
 * <p>需求1：展示数据库里的文本</p>
 * <p>需求2：如果文本中含有 AchoBeta Polaris 或 AchoBeta x.0, 需要进行<b>加粗</b>处理</p>
 * <p>需求3：接口健全，无权限的用户无法访问。现在有两个部门都需要用到该文本展示方法，请兼容</p>
 * <p>需求4：不同部门，展示效果不同，部门A想要实现<font color="white"><b>关键词加粗</b></font>效果，部门B想要实现<font color="red">关键词变红</font>效果</p>
 * @create 2024/11/3
 */
@Service
@RequiredArgsConstructor
public class ReadService4 {

    public static final String REPLACE_VAR_REGEX = "(AchoBeta\\s+\\d+\\.\\d+|AchoBeta\\s+Polaris)";
    private final TextDAO textDAO;


    public String read(UserInfo userInfo, String textId) {
        // 需求3: 直接生硬的在 read 方法插入鉴权的 if - else
        if (!AuthHolder.authGroup(userInfo)) {
            throw new RuntimeException("鉴权失败! 部门 " + userInfo.getName() + " 暂无访问 read 方法权限!");
        }
        String text = textDAO.getTextById(textId);
        // 需求4: 增添 if - else 应对不同部门展示不同效果的逻辑
        if ("AchoBeta 5.0".equals(userInfo.getName())) {
            return PatternStrUtil.replaceText(text, "<b>", "</b>");
        }
        if ("AchoBeta 6.0".equals(userInfo.getName())) {
            return PatternStrUtil.replaceText(text, "<font color=\"red\">", "</font>");
        }
        return null;
    }

}
