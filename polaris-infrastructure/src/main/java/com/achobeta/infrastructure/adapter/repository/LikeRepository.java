package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.like.adapter.repository.ILikeRepository;
import com.achobeta.infrastructure.dao.LikeMapper;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author huangwenxing
 * @description
 * @date 2024/11/18
 */
@Repository
@Slf4j
public class LikeRepository implements ILikeRepository {
    /**mysql服务*/
    @Resource
    private LikeMapper mapper;
    /**redis服务*/
    @Resource
    private RedissonService redissonService;
    /**键过期时间500ms*/
    private long expired = 500;
    @Override
    @Transactional
    public void like(String fromId, String toId, boolean liked) {
        /**更新点赞状态*/
        Integer row = mapper.updateLiked(fromId, toId, liked);
        if(row==0){
            mapper.insertLiked(fromId, toId, liked);
        }
        /**更新点赞数量*/
        int count = liked?1:-1;
        mapper.addLikeCount(toId, count);
    }

    @Override
    public void setLikeKey(String fromId, String toId) {
        String key = Constants.LIKE_PRE_KEY + fromId + toId;
        redissonService.setValue(key,Constants.LIKE_VALUE,expired);
    }

    @Override
    public String getLikeValue(String fromId, String toId) {
        String key = Constants.LIKE_PRE_KEY + fromId + toId;
        return redissonService.getValue(key);
    }

    @Override
    public boolean queryLikedById(String fromId, String toId) {
        Integer liked = mapper.queryLikedById(fromId, toId);
        if(liked==null){
            return false;
        }
        return liked == 1;
    }

    @Override
    public Integer queryUserById(String userId) {
        return mapper.queryUserById(userId);
    }
}
