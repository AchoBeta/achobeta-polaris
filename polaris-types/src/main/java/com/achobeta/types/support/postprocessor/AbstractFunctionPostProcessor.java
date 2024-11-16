package com.achobeta.types.support.postprocessor;

import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chensongmin
 * @description 主次流程执行模版基类(函数式版)，用模版方法定义分支流程和主流程
 * <p>如果需要定制化主次流程执行逻辑，继承该类 {@link AbstractFunctionPostProcessor} 重写 doPostProcessor 方法即可</p>
 * @create 2024/11/3
 */
@Slf4j
public abstract class AbstractFunctionPostProcessor<E> {

    /**
     * 函数式主次流程执行模版方法
     * <pre>
     * {@code 使用说明：
     * public RenderBookVO renderBook(String userId, String bookId) { 
     *   PostContext<RenderBO> renderContext = buildPostContext(userId, bookId);
     *   renderContext = super.doPostProcessor(renderContext, RenderBookPostProcessor.class,
     *     // 函数式接口 Function 扩展写法
     *     postContext -> {
     *       log.info("重写前置校验中断主流程逻辑方法 ... ");
     *       return postContext;
     *     },
     *     postContext -> {
     *       RenderBO renderBO = postContext.getBizData();
     *       UserEntity userEntity = renderBO.getUserEntity();
     *       BookEntity bookEntity = renderBO.getBookEntity();
     *
     *       bookEntity = repository.queryBook(bookEntity.getBookId());
     *
     *       postContext.setBizData(
     *           RenderBO.builder().userEntity(userEntity).bookEntity(bookEntity).build());
     *       return postContext;
     *     });
     *   return RenderBookVO.builder().build();
     * }}
     * </pre>
     * @param postContext 上下文
     * @param postProcessor
     * @param doInterruptMainFunction 中断主流程执行函数式接口
     * @param doMainFunction 主流程执行函数式接口
     * @return 上下文
     * @param <T>
     */
    public <T> PostContext<E> doPostProcessor(PostContext<E> postContext, Class<T> postProcessor,
        Function<PostContext<E>, PostContext<E>> doInterruptMainFunction,
        Function<PostContext<E>, PostContext<E>> doMainFunction) {
        PostProcessorContainer<T> postProcessorContainer =
                PostProcessorContainer.getInstance(postProcessor);

        // 分支流程[前置扩展]逻辑
        log.info("模块:{} 分支流程[前置扩展]逻辑开始执行！",
                postContext.getBizName());
        boolean isContinue = postProcessorContainer.doHandleBefore(postContext);

        if (!isContinue) {
            log.info("模块:{} 前置逻辑执行决策：中断主流程执行！",
                    postContext.getBizName());
            return doInterruptMainFunction.apply(postContext);
        } else {
            log.info("模块:{} 前置逻辑执行完毕，当前决策为：不干预主流程执行！",
                    postContext.getBizName());
        }

        // 主流程逻辑
        log.info("模块:{} 主流程逻辑开始执行！",
                postContext.getBizName());
        postContext = doMainFunction.apply(postContext);
        log.info("模块:{} 主流程逻辑执行完毕！",
                postContext.getBizName());

        // 分支流程[后置扩展]逻辑
        log.info("模块:{} 分支流程[后置扩展]逻辑开始执行！",
                postContext.getBizName());
        postProcessorContainer.doHandleAfter(postContext);
        log.info("模块:{} 分支流程[后置扩展]逻辑执行结束！",
                postContext.getBizName());

        return postContext;
    }

    /**
     * 函数式主次流程执行模版方法 2
     * <pre>
     * {@code 使用说明
     * public RenderBookVO renderBook(String userId, String bookId) {
     *   PostContext  renderContext = buildPostContext(userId, bookId);
     *   renderContext = super.doPostProcessor(renderContext, RenderBookPostProcessor.class,
     *       // 抽象内部类扩展写法
     *       new AbstractPostProcessorOperation<RenderBO>() {
     *         @Override
     *         public PostContext<RenderBO> doInterruptMainProcessor (
     *             PostContext < RenderBO > postContext) {
     *           log.info("重写前置校验中断主流程逻辑方法 ... ");
     *           return postContext;
     *         }
     *
     *         @Override
     *         public PostContext<RenderBO> doMainProcessor (PostContext < RenderBO > postContext) {
     *           RenderBO renderBO = postContext.getBizData();
     *           UserEntity userEntity = renderBO.getUserEntity();
     *           BookEntity bookEntity = renderBO.getBookEntity();
     *
     *           bookEntity = repository.queryBook(bookEntity.getBookId());
     *
     *           postContext.setBizData(
     *               RenderBO.builder().userEntity(userEntity).bookEntity(bookEntity).build());
     *           return postContext;
     *         }
     *       });
     *   return RenderBookVO.builder().build();
     * }}
     * </pre>
     * @param postContext
     * @param postProcessor
     * @param op
     * @return
     * @param <T>
     */
    public <T> PostContext<E> doPostProcessor(PostContext<E> postContext, Class<T> postProcessor,
        final AbstractPostProcessorOperation<E> op) {
        PostProcessorContainer<T> postProcessorContainer =
            PostProcessorContainer.getInstance(postProcessor);

        // 分支流程[前置扩展]逻辑
        log.info("模块:{} 分支流程[前置扩展]逻辑开始执行！",
            postContext.getBizName());
        boolean isContinue = postProcessorContainer.doHandleBefore(postContext);

        if (!isContinue) {
            log.info("模块:{} 前置逻辑执行决策：中断主流程执行！",
                postContext.getBizName());
            return op.doInterruptMainProcessor(postContext);
        } else {
            log.info("模块:{} 前置逻辑执行完毕，当前决策为：不干预主流程执行！",
                postContext.getBizName());
        }

        // 主流程逻辑
        log.info("模块:{} 主流程逻辑开始执行！",
            postContext.getBizName());
        postContext = op.doMainProcessor(postContext);
        log.info("模块:{} 主流程逻辑执行完毕！",
            postContext.getBizName());

        // 分支流程[后置扩展]逻辑
        log.info("模块:{} 分支流程[后置扩展]逻辑开始执行！",
            postContext.getBizName());
        postProcessorContainer.doHandleAfter(postContext);
        log.info("模块:{} 分支流程[后置扩展]逻辑执行结束！",
            postContext.getBizName());

        return postContext;
    }

    public abstract static class AbstractPostProcessorOperation<E> {

        public abstract PostContext<E> doMainProcessor(PostContext<E> postContext);

        public PostContext<E> doInterruptMainProcessor(PostContext<E> postContext) {
            return postContext;
        }

    }

}
