package com.achobeta.read.good_design.biz;

import com.achobeta.read.PatternStrUtil;
import com.achobeta.read.good_design.ReaderBO;
import com.achobeta.read.good_design.TextReadPostProcessor;
import com.achobeta.read.good_design.postprocessor.PostContext;
import org.springframework.stereotype.Component;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
@Component
public class GroupBReadPostProcessor implements TextReadPostProcessor {

    @Override
    public void handleAfter(PostContext<ReaderBO> postContext) {
        ReaderBO readerBO = postContext.getBizData();
        if ("AchoBeta 6.0".equals(readerBO.getUser().getName())) {
            String text = PatternStrUtil.replaceText(readerBO.getText(), "<font color=\"red\">", "</font>");
            readerBO.setText(text);
            postContext.setBizData(readerBO);
        }
    }

}
