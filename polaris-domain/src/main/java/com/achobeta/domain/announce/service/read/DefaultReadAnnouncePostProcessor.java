package com.achobeta.domain.announce.service.read;

import com.achobeta.domain.announce.adapter.repository.IAnnounceRepository;
import com.achobeta.domain.announce.model.entity.ReadAnnounceEntity;
import com.achobeta.domain.announce.service.IReadAnnounceService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @author huangwenxing
 * @description 默认读公告实现类
 * @date 2024/11/13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultReadAnnouncePostProcessor extends AbstractPostProcessor<ReadAnnounceEntity> implements IReadAnnounceService {
    private final IAnnounceRepository repository;

    @Override
    public void readAnnounce(String userId, String announceId) {
        PostContext postContext = buildPostContext(userId, announceId);
        super.doPostProcessor(postContext, ReadAnnouncePostProcessor.class);
        Integer i = (Integer)postContext.getExtra().get(Constants.AFFECT_LENGTH);
        if(i==0){
          throw new AppException(String.valueOf(GlobalServiceStatusCode.PARAM_NOT_VALID.getCode()),GlobalServiceStatusCode.PARAM_NOT_VALID.getMessage());
        }
    }

    private static PostContext<Object> buildPostContext(String userId, String announceId) {
        return PostContext.builder()
                .bizId(BizModule.READANNOUNCE.getCode())
                .bizName(BizModule.READANNOUNCE.getName())
                .bizData(ReadAnnounceEntity.builder()
                        .AnnounceId(announceId)
                        .userId(userId)
                        .build())
                .build();
    }

    @Override
    public PostContext doMainProcessor(PostContext<ReadAnnounceEntity> postContext) {
        Integer i = repository.readAnnounce(postContext.getBizData().getUserId(), postContext.getBizData().getAnnounceId());
        postContext.addExtraData(Constants.AFFECT_LENGTH,i);
        return postContext;
    }
}
