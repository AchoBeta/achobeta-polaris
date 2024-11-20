package com.achobeta.domain.like.service.like.extendbiz;

import com.achobeta.domain.like.adapter.repository.ILikeRepository;
import com.achobeta.domain.like.model.LikeEntity;
import com.achobeta.domain.like.service.like.LikePostProcessor;
import com.achobeta.types.common.Constants;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huangwenxing
 * @description 接口幂等性扩展点实现
 * @create 2024/11/19
 */
@Slf4j
@Component
public class DuplicateCheckPostProcessor implements LikePostProcessor {
    @Resource
    private ILikeRepository repository;

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean handleBefore(PostContext<LikeEntity> postContext) {
        /**检查是否为重复请求*/
        LikeEntity like = postContext.getBizData();
        String likeValue = repository.getLikeValue(like.getFromId(), like.getToId());
        if(Constants.LIKE_VALUE.equals(likeValue)){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.REQUEST_NOT_VALID.getCode()),GlobalServiceStatusCode.REQUEST_NOT_VALID.getMessage());
        }
        repository.setLikeKey(like.getFromId(),like.getToId());
        /**检查获赞人是否存在*/
        Integer i = repository.queryUserById(like.getToId());
        if (i==0){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST),GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        /**检查点赞状态对否一致*/
        boolean b = repository.queryLikedById(like.getFromId(), like.getToId());
        if(b==like.isLiked()){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.REQUEST_NOT_VALID.getCode()),GlobalServiceStatusCode.REQUEST_NOT_VALID.getMessage());
        }
        return true;
    }
}
