package com.achobeta.domain.user.adapter.port;

/**
 * @author yangzhiyao
 * @description 点赞状态外部接口
 * @date 2024/11/8
 */
public interface ILikeStatusPort {

    /**
     * 获取用户对于自己的点赞状态
     * 查询不到也默认是未点赞
     * @param userId 用户id
     * @return true表示已点赞，false表示未点赞
     */
    Boolean isLiked(String userId);

}
