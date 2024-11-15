package com.achobeta.domain.announce.service;

/**
 * @author huangwenxing
 * @description 读公告服务统一接口
 * @date 2024/11/13
 */
public interface IReadAnnounceService {
    /**获取单页公告*/
    void readAnnounce(String userId,String announceId);
}
