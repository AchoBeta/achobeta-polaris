package com.achobeta.read.good_design.biz;

import com.achobeta.read.PatternStrUtil;
import com.achobeta.read.good_design.ReaderBO;
import com.achobeta.read.good_design.TextReadPostProcessor;
import com.achobeta.read.good_design.postprocessor.PostContext;
import org.springframework.stereotype.Component;

/**
 * @author chensongmin
 * @description 部门 A 的个性化扩展逻辑
 * @create 2024/11/3
 */
@Component
public class GroupAReadPostProcessor implements TextReadPostProcessor {
    
    @Override
    public void handleAfter(PostContext<ReaderBO> postContext) {
        ReaderBO readerBO = postContext.getBizData();
        if ("AchoBeta 5.0".equals(readerBO.getUser().getName())) {
            String text = PatternStrUtil.replaceText(readerBO.getText(), "<b>", "</b>");
            readerBO.setText(text);
            postContext.setBizData(readerBO);
        }
    }

}
