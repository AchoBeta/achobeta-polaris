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
 * <p>需求3：接口鉴权，无权限的用户无法访问。现在有两套系统都需要用到该文本展示方法，请兼容</p>
 * @create 2024/11/3
 */
@Service
@RequiredArgsConstructor
public class ReadService3 {

    private final TextDAO textDAO;


    public String read(UserInfo userInfo, String textId) {
        // 需求3: 直接生硬的在 read 方法插入鉴权的 if - else
        if (!AuthHolder.authGroup(userInfo)) {
            throw new RuntimeException("鉴权失败! 部门 " + userInfo.getName() + " 暂无访问 read 方法权限!");
        }
        String text = textDAO.getTextById(textId);
        return PatternStrUtil.replaceText(text, "<b>", "</b>");
    }

}
