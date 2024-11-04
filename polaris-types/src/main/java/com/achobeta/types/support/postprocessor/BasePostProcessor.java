package com.achobeta.types.support.postprocessor;

/**
 * @author chensongmin
 * @description 扩展接口定义与统一驱动接口类
 * <p>扩展点用于主流程和分支流程的连接桥梁，也是后续需求可扩展的切入点，应当抽象成共性方法</p>
 * @create 2024/11/3
 */
public interface BasePostProcessor <T> {

    /**
     * <p>前置处理器，负责驱动扩展点集合前置方法</p>
     * <p>boolean 类型，用于在执行完前置处理后判断是否需要干预主流程的执行</p>
     * <p>1. 如果返回值为 true，会继续执行主流程</p>
     * <p>2. 如果想要定制成前置过滤失败切断主流程执行，重写返回值为 false</p>
     * @param postContext
     * @return 默认放行
     */
    default boolean handleBefore(PostContext<T> postContext) {
        return true;
    }

    /**
     * <p>后置处理器，负责驱动扩展点集合后置方法</p>
     * @param postContext
     */
    default void handleAfter(PostContext<T> postContext) {

    };

    /**
     * 设置扩展点优先级
     * @return 默认值为 0
     */
    default int getPriority() {
        return 0;
    }

}
