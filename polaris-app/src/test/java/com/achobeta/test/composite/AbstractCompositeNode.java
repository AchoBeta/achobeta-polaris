package com.achobeta.test.composite;

import java.util.List;

/**
 * @author chensongmin
 * @description
 * component 抽象角色，叶子节点和非叶子节点都需要继承该抽象角色
 *
 * <pre> what is composite design pattern？
 * as i n，the composite design pattern lets clients uniformly treat
 * individual objects and compositions of objects </pre>
 * <pre> 什么是组合模式
 * 他的出现让客户端能够用统一的方式对待单一对象和对象组合
 * </pre>
 * @date 2024/11/15
 */
public abstract class AbstractCompositeNode {

    public static final String DEL_NODE_AND_CHILD = "删除节点以及其孩子";
    public static final String DEL_NODE_ONLY = "仅删除当前节点";

    protected void addCompositeNode(AbstractCompositeNode node) {
        throw new UnsupportedOperationException("Not Support node add!");
    }

    protected void delCompositeNode(AbstractCompositeNode node) {
        throw new UnsupportedOperationException("Not Support node remove!");
    }

    public abstract AbstractCompositeNode generateCompositeTree(List<PositionEntity> positionEntityList);
}
