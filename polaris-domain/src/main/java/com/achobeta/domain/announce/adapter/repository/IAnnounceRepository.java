package com.achobeta.domain.announce.adapter.repository;

import com.achobeta.domain.announce.model.entity.AnnounceEntity;

import java.util.List;

/**
 * @author huangwenxing
 * @description 查询公告仓储接口
 * @date 2024/11/10
 */
public interface IAnnounceRepository {
    /**
     *
     * @param userId 用户id
     * @param lastAnnounceId 页面最后一条公告的id
     * @return 设备实体
     */
    List<AnnounceEntity> queryAnnouncesByUserId(String userId,Integer limit,String lastAnnounceId);
}
