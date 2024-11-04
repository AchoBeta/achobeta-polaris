package com.achobeta.domain.render.service.render;


import com.achobeta.domain.render.model.bo.RenderBO;
import com.achobeta.types.support.postprocessor.BasePostProcessor;

/**
 * @author chensongmin
 * @description 文本渲染扩展接口
 * <p>基于扩展接口 BasePostProcessor，根据文本渲染需求这个特定场景所细化下来的扩展接口，符合接口隔离原则 ISP</p>
 * <p>否则如果所有场景都是基于 BasePostProcessor 进行扩展实现，很难区分出某一模块的扩展子类实现</p>
 * @create 2024/11/3
 */
public interface RenderBookPostProcessor extends BasePostProcessor<RenderBO> { }