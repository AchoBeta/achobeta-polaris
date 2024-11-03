package com.achobeta.read.good_design;

import com.achobeta.read.good_design.postprocessor.PostContext;
import com.achobeta.read.good_design.postprocessor.PostProcessorContainer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chensongmin
 * @description 主次流程执行模版基类，用模版方法定义分支流程和主流程
 * <p>如果需要定制化主次流程执行逻辑，继承该类 {@link AbstractPostProcessor} 重写 doPostProcessor 方法即可</p>
 * @create 2024/11/3
 */
@Slf4j
public abstract class AbstractPostProcessor <E> {
    
    public <T> PostContext<E> doPostProcessor(Class<T> postProcessor, PostContext<E> postContext) {
        PostProcessorContainer<T> postProcessorContainer =
                PostProcessorContainer.getInstance(postProcessor);

        // 分支流程[前置扩展]逻辑
        log.info("bizId: {}, bizName: {} - 分支流程[前置扩展]逻辑开始执行！",
                postContext.getBizId(), postContext.getBizName());
        boolean isContinue = postProcessorContainer.doHandleBefore(postContext, PostProcessorContainer.AND_MODEL);

        if (!isContinue) {
            log.info("bizId: {}, bizName: {} - 前置逻辑执行完毕，当前决策为：中断主流程执行！",
                    postContext.getBizId(), postContext.getBizName());
            return doInterruptMainProcessor(postContext);
        } else {
            log.info("bizId: {}, bizName: {} - 前置逻辑执行完毕，当前决策为：不干预主流程执行！",
                    postContext.getBizId(), postContext.getBizName());
        }

        // 主流程逻辑
        log.info("bizId: {}, bizName: {} - 主流程逻辑开始执行！",
                postContext.getBizId(), postContext.getBizName());
        postContext = doMainProcessor(postContext);
        log.info("bizId: {}, bizName: {} - 主流程逻辑执行完毕！",
                postContext.getBizId(), postContext.getBizName());

        // 分支流程[后置扩展]逻辑
        log.info("bizId: {}, bizName: {} - 分支流程[后置扩展]逻辑开始执行！",
                postContext.getBizId(), postContext.getBizName());
        postProcessorContainer.doHandleAfter(postContext);
        log.info("bizId: {}, bizName: {} - 分支流程[后置扩展]逻辑执行结束！",
                postContext.getBizId(), postContext.getBizName());

        return postContext;
    }

    public abstract PostContext<E> doMainProcessor(PostContext<E> postContext);

    public PostContext<E> doInterruptMainProcessor(PostContext<E> postContext) {
        return postContext;
    }

}
