package com.achobeta.domain.user.adapter.port;

/**
 * @author yangzhiyao
 * @description 点赞状态外部接口
 * @date 2024/11/8
 */
public interface ILikeStatusPort {

    Boolean isLiked(String userId);

}
