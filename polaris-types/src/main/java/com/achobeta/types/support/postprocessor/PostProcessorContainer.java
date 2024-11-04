package com.achobeta.types.support.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author chensongmin
 * @description 扩展点集成工具类
 * 负责扩展点的收集与驱动
 * @create 2024/11/3
 */
@Slf4j
@Component
public class PostProcessorContainer <T> implements ApplicationContextAware {

    private Class<T> postProcessorClassList;

    /**
     * <p>需要 ApplicationContext 提供的 getBeanOfType() 来获取接口实现类集合</p>
     * <p>Spring 内部 Bean 无法通过正常方式注入，只能通过 Aware 接口透传过来</p>
     * <p><b>Aware 接口实际上是一种迪米特法则 IoD</b></p>
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PostProcessorContainer.applicationContext = applicationContext;
    }

    /**
     * 单例模式，不对外暴露构造方法
     */
    private PostProcessorContainer() {}

    /**
     * 使用静态内部类生成单例的扩展点集成收集器
     * <p>当 PostProcessorContainer 类加载时，静态内部类 SingletonHolder 没有被加载进内存。
     * 只有当调用 getUniqueInstance() 方法从而触发 SingletonHolder.INSTANCE 时
     * SingletonHolder 才会被加载，此时初始化 INSTANCE 实例</p>
     * <p><b>JVM在类初始化阶段会对类加上类锁，防止多线程并发初始化同一个类</b></p>
     */
    private static class SingletonHolder {
        private static final PostProcessorContainer INSTANCE = new PostProcessorContainer<>();
    }

    @SuppressWarnings("all")
    public static <T> PostProcessorContainer getInstance(Class<T> postProcessorClassList) {
        SingletonHolder.INSTANCE.postProcessorClassList = postProcessorClassList;
        return SingletonHolder.INSTANCE;
    }

    /**
     * 前置执行器驱动方法
     * <p>通过 ApplicationContext 获取 <? extends BasePostProcessor> 接口类型下的
     * 所有扩展实现子类，按照优先级的顺序组织成链表串联执行。</p>
     * <br>如果前置执行回返了 false，说明前置流程执行失败，
     * 将干预主流程的执行。
     * </p>
     * @param postContext 数据传输上下文
     * @return 前置流程都通过，返回值才是 true，否则只要执行到第一个 false 都会直接终止后续校验
     * @param <E> PostContext<T> 类型
     */
    @SuppressWarnings("all")
    public <E extends PostContext<?>> boolean doHandleBefore(E postContext) {
        List<? extends BasePostProcessor<T>> postProcessorList = getBeanOfType(postProcessorClassList);
        if (CollectionUtils.isEmpty(postProcessorList)) {
            return true;
        }
        boolean isContinue = true;
        // 优先级越高，越靠近主流程
        postProcessorList.sort(Comparator.comparing(BasePostProcessor::getPriority));

        for (BasePostProcessor<T> postProcessor : postProcessorList) {
            isContinue = isContinue && postProcessor.handleBefore((PostContext<T>) postContext);
            // 出现第一个不通过，后续流程都不执行
            if (!isContinue) return isContinue;
        }
        return isContinue;
    }

    /**
     * 后置执行器驱动方法
     * <p>通过 ApplicationContext 获取 <? extends BasePostProcessor> 接口类型下的
     * 所有扩展实现子类，按照优先级的顺序组织成链表串联执行。</p>
     * @param postContext 数据传输上下文
     * @param <E> PostContext<T> 类型
     */
    public <E extends PostContext<?>> void doHandleAfter(E postContext) {
        List<? extends BasePostProcessor<T>> postProcessorList = getBeanOfType(postProcessorClassList);
        if (CollectionUtils.isEmpty(postProcessorList)) {
            return ;
        }

        // 优先级越高，越靠近主流程
        postProcessorList.sort(Comparator.comparing(BasePostProcessor::getPriority));

        for (BasePostProcessor<T> postProcessor : postProcessorList) {
            postProcessor.handleAfter((PostContext<T>) postContext);
        }
    }

    @SuppressWarnings("unchecked")
    private List<? extends BasePostProcessor<T>> getBeanOfType(Class<T> postProcessorClassList) {
        Map<String, T> postProcessorMap = applicationContext.getBeansOfType(postProcessorClassList);
        return (List<? extends BasePostProcessor<T>>) new ArrayList<>(postProcessorMap.values());
    }

}
