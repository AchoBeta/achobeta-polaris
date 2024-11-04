package com.achobeta.read.good_design.biz;

import com.achobeta.read.AuthHolder;
import com.achobeta.read.good_design.ReaderBO;
import com.achobeta.read.good_design.TextReadPostProcessor;
import com.achobeta.read.good_design.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chensongmin
 * @description 白名单校验扩展实现
 * 这里隐藏的一个逻辑是白名单优先级一定是低于用户组的优先级的，因此需要重写优先级
 * @create 2024/11/3
 */
@Slf4j
@Component
public class WhiteListAuthPostProcessor implements TextReadPostProcessor {

    @Override
    public void handleAfter(PostContext<ReaderBO> postContext) {
        ReaderBO readerBO = postContext.getBizData();
        if (AuthHolder.isInWhiteList(readerBO.getUser())) {
            // "这是 AchoBeta Polaris 北极星系统".length() = 25
            String text = readerBO.getText().substring(0, 25) + "... 【想看更多内容，请关注 AchoBeta！】";
            readerBO.setText(text);
            postContext.setBizData(readerBO);
        }
    }

    @Override
    public int getPriority() {
        return -1;
    }
}
