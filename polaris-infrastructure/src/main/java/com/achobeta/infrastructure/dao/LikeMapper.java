package com.achobeta.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huangwenxing
 * @description 点赞Dao接口
 * @data 2024/11/18
 * */
@Mapper
public interface LikeMapper {
    /**点赞表更新点赞状态*/
    Integer updateLiked(@Param("fromId") String fromId, @Param("toId") String toId, @Param("liked") boolean liked);
    /**用户表增加点赞数量*/
    Integer addLikeCount(@Param("userId") String userId,@Param("likeCount") int likeCount);
    /**插入用户点赞状态*/
    Integer insertLiked(@Param("fromId") String fromId,@Param("toId") String toId,@Param("liked") boolean liked);
    /**查询用户点赞状态*/
    Integer queryLikedById(@Param("fromId") String fromId,@Param("toId") String toId);
    /**查询用户是否存在*/
    Integer queryUserById(@Param("userId") String userId);
}
