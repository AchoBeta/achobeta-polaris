package com.achobeta.domain.announce.service;

import com.achobeta.domain.announce.model.valobj.UserAnnounceVO;

/**
 * @author huangwenxing
 * @description 公告渲染服务统一接口
 * @date 2024/11/11
 */
public interface IAnnounceService {
    /**获取单页公告*/
    UserAnnounceVO queryAnnouncesByUserId(String userId,Integer limit,String lastAnnounceId);
}
