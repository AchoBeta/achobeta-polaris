package com.achobeta.domain.like.service.like;

import com.achobeta.domain.like.adapter.repository.ILikeRepository;
import com.achobeta.domain.like.model.LikeEntity;
import com.achobeta.domain.like.service.ILikeService;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractFunctionPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @author huangwenxing
 * @description 默认点赞服务实现类
 * @date 2024/11/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultLikeService extends AbstractFunctionPostProcessor implements ILikeService {
    private final ILikeRepository repository;
    @Override
    public void Like(String fromId, String toId, boolean liked) {
        PostContext postContext = buildPostContext(fromId, toId, liked);
        super.doPostProcessor(postContext, LikePostProcessor.class, new AbstractPostProcessorOperation<LikeEntity>() {
            @Override
            public PostContext doMainProcessor(PostContext<LikeEntity> postContext) {
                LikeEntity like = postContext.getBizData();
                repository.like(like.getFromId(), like.getToId(), like.isLiked());
                return postContext;
            }
        });
    }

    private static PostContext<Object> buildPostContext(String fromId, String toId, boolean liked) {
        return PostContext.builder()
                .bizId(BizModule.LIKE.getCode())
                .bizName(BizModule.LIKE.getName())
                .bizData(LikeEntity.builder()
                        .fromId(fromId)
                        .toId(toId)
                        .liked(liked)
                        .build())
                .build();
    }
}
