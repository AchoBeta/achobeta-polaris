package com.achobeta.read.good_design.postprocessor;

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

    public static final String AND_MODEL = "AND";
    public static final String OR_MODEL = "OR";

    private Class<T> postProcessorClassList;

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
     * 使用静态类工厂方法生成单例的扩展点集成收集器
     * 静态方法会在项目启动之初加上类锁，不会出现并发问题
     * @param postProcessorClassList
     * @return
     * @param <T>
     */
    public static <T> PostProcessorContainer getInstance(Class<T> postProcessorClassList) {
        PostProcessorContainer postProcessorContainer = new PostProcessorContainer<>();
        postProcessorContainer.postProcessorClassList = postProcessorClassList;
        return postProcessorContainer;
    }

    /**
     *
     * @param postContext
     * @param model 选择与模式、或模式
     * @return
     * @param <E>
     */
    public <E extends PostContext<?>> boolean doHandleBefore(E postContext, String model) {
        List<? extends BasePostProcessor<T>> postProcessorList = getBeanOfType(postProcessorClassList);
        if (CollectionUtils.isEmpty(postProcessorList)) {
            return true;
        }
        boolean isContinue = true;
        // 优先级越高，越靠近主流程
        postProcessorList.sort(Comparator.comparing((BasePostProcessor<T> o) -> o.getPriority()));
//        postProcessorList.sort(Comparator.comparing(BasePostProcessor::getPriority));

        for (BasePostProcessor<T> postProcessor : postProcessorList) {
            if (AND_MODEL.equals(model)) {
                isContinue &= postProcessor.handleBefore((PostContext<T>) postContext);
            } else if (OR_MODEL.equals(model)) {
                isContinue |= postProcessor.handleBefore((PostContext<T>) postContext);
            }
        }
        return isContinue;
    }

    /**
     *
     * @param postContext
     * @param <E>
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
