package com.achobeta.domain.like.service;
/**
 * @author huangwenxing
 * @description 点赞服务统一接口
 * @date 2024/11/17
 */
public interface ILikeService {
    void Like(String fromId,String toId,boolean liked);
}
