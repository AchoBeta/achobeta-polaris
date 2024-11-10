package com.achobeta.domain.render.service;

import com.achobeta.domain.render.model.valobj.RenderBookVO;

/**
 * @author BanTanger 半糖
 * @description 文本渲染服务统一接口
 * @date 2024/11/4
 */
public interface IRenderTextService {

    /**
     * 文本渲染(ceshui)
     * 121231
     * 15161嗡嗡嗡嗡嗡嗡
     * 469465
     * @param userId
     * @param bookId
     * @return
     */
    RenderBookVO renderBook(String userId, String bookId);

}
