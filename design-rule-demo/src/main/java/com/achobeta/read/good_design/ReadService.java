package com.achobeta.read.good_design;

import com.achobeta.read.TextDAO;
import com.achobeta.read.UserInfo;
import com.achobeta.read.good_design.postprocessor.PostContext;
import com.achobeta.read.good_design.postprocessor.PostProcessorContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
@Slf4j
@Service
public class ReadService extends AbstractPostProcessor<ReaderBO> {

    @Resource
    private TextDAO textDAO;

    public String reader(UserInfo userInfo, String textId) {
        PostProcessorContainer<TextReadPostProcessor> postProcessorContainer =
                PostProcessorContainer.getInstance(TextReadPostProcessor.class);

        ReaderBO readerBO = ReaderBO.builder()
                .user(userInfo)
                .textId(textId)
                .build();

        PostContext<ReaderBO> readerContext = PostContext.<ReaderBO>builder()
//                .bizId(SnowflakeIdUtil.nextIdStr())
                .bizName("文本渲染")
                .bizData(readerBO)
                .build();

        // 分支流程[前置扩展]逻辑
        log.info("模块:{} 分支流程[前置扩展]逻辑开始执行！",
                readerContext.getBizName());
        boolean isContinue = postProcessorContainer.doHandleBefore(readerContext);

        if (!isContinue) {
            log.info("模块:{} 前置逻辑执行完毕，当前决策为：中断主流程执行！",
                    readerContext.getBizName());
            return readerBO.getText();
        } else {
            log.info("模块:{} 前置逻辑执行完毕，当前决策为：不干预主流程执行！",
                    readerContext.getBizName());
        }

        // 主流程逻辑
        log.info("模块:{} 主流程逻辑开始执行！",
                readerContext.getBizName());
        String text = textDAO.getTextById(textId);
        readerBO.setText(text);
        readerContext.setBizData(readerBO);
        log.info("模块:{} 主流程逻辑执行完毕！",
                readerContext.getBizName());

        // 分支流程[后置扩展]逻辑
        log.info("模块:{} 分支流程[后置扩展]逻辑开始执行！",
                readerContext.getBizName());
        postProcessorContainer.doHandleAfter(readerContext);
        log.info("模块:{} 分支流程[后置扩展]逻辑执行结束！",
                readerContext.getBizName());

        return readerContext.getBizData().getText();
    }

    public String readTemplate(UserInfo userInfo, String textId) {
        ReaderBO readerBO = ReaderBO.builder().user(userInfo).textId(textId).build();
        PostContext<ReaderBO> readerContext = PostContext.<ReaderBO>builder().bizName("文本渲染").bizData(readerBO).build();

        readerContext = super.doPostProcessor(TextReadPostProcessor.class, readerContext);
        return readerContext.getBizData().getText();
    }

    @Override
    public PostContext<ReaderBO> doMainProcessor(PostContext<ReaderBO> postContext) {
        ReaderBO readerBO = postContext.getBizData();
        String text = textDAO.getTextById(readerBO.getTextId());
        readerBO.setText(text);
        postContext.setBizData(readerBO);
        return postContext;
    }

    @Override
    public PostContext<ReaderBO> doInterruptMainProcessor(PostContext<ReaderBO> postContext) {
        log.info("重写前置校验中断主流程逻辑方法 ... ");
        return postContext;
    }
}
