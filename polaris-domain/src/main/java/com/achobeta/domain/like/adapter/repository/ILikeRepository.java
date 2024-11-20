package com.achobeta.domain.like.adapter.repository;
/**
 * @author huangwenxing
 * @description 点赞仓储接口
 * @date 2024/11/18
 */
public interface ILikeRepository {
    /**
     * mysql更新点赞
     * @param fromId  点赞人
     * @param toId   获赞人
     * @param liked 点赞状态
     */
    void like(String fromId, String toId, boolean liked);

    /**
     * 检查是否为重复的请求，redis设置点赞的值
     * @param fromId 点赞人
     * @param toId 获赞人
     */
    void setLikeKey(String fromId, String toId);
    /**
     * redis获得点赞的值
     * @param fromId 点赞人
     * @param toId 获赞人
     */
    String getLikeValue(String fromId, String toId);

    /**
     *  查询用户点赞状态
     * @param fromId 点赞人id
     * @param toId 获赞人id
     * @return
     */

    boolean queryLikedById(String fromId, String toId);

    /**
     * 查询用户是否存在
     * @param userId 获赞人id
     * @return
     */
    Integer queryUserById(String userId);
}
