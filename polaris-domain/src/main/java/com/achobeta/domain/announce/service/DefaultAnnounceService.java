package com.achobeta.domain.announce.service;

import com.achobeta.domain.announce.adapter.repository.IAnnounceRepository;
import com.achobeta.domain.announce.model.bo.AnnounceBO;
import com.achobeta.domain.announce.model.entity.*;
import com.achobeta.domain.announce.model.valobj.UserAnnounceVO;
import com.achobeta.domain.announce.service.get.AnnounceCountPostProcessor;
import com.achobeta.domain.announce.service.get.AnnouncePostProcessor;
import com.achobeta.domain.announce.service.read.ReadAllAnnouncePostProcessor;
import com.achobeta.domain.announce.service.read.ReadAnnouncePostProcessor;
import com.achobeta.types.common.Constants;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.AbstractFunctionPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
/**
 * @author huangwenxing
 * @description 默认公告渲染服务实现类
 * @date 2024/11/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAnnounceService extends AbstractFunctionPostProcessor implements IAnnounceService {
    private final IAnnounceRepository repository;
    @Override
    public UserAnnounceVO queryAnnouncesByUserId(String userId, Integer limit, String lastAnnounceId) {
        PostContext<AnnounceBO> queryAnnouncesContext = buildPostContext(userId, limit, lastAnnounceId);
        queryAnnouncesContext = super.doPostProcessor(queryAnnouncesContext, AnnouncePostProcessor.class,
                new AbstractPostProcessorOperation<AnnounceBO>() {
                    @Override
                    public PostContext<AnnounceBO> doMainProcessor(PostContext<AnnounceBO> postContext) {
                        AnnounceBO announceBO = postContext.getBizData();
                        UserAnnounceEntity userAnnounceEntity = announceBO.getUserAnnounceEntity();
                        PageResult pageResult = announceBO.getPageResult();
                        //将前端传来的长度加一
                        List<AnnounceEntity> announceEntities = repository.queryAnnouncesByUserId(userAnnounceEntity.getUserId(), pageResult.getLimit()+1, pageResult.getLastAnnounceId());

                        if (CollectionUtils.isEmpty(announceEntities)){
                            log.error("公告不存在！userId：{},limit:{},lastAnnounceId:{}",userAnnounceEntity.getUserId(),pageResult.getLimit(),pageResult.getLastAnnounceId());
                            throw new AppException(String.valueOf(GlobalServiceStatusCode.PARAM_NOT_VALID.getCode()),GlobalServiceStatusCode.PARAM_NOT_VALID.getMessage());
                        }
                        //判断还有没有更多数据
                        boolean flag = announceEntities.size() > pageResult.getLimit();
                        if (flag){
                            //有就移除最后一个并返回给前端
                            announceEntities.remove(announceEntities.size()-1);
                        }

                        postContext.addExtraData(Constants.NEXT_PAGE,flag);

                        userAnnounceEntity.setUserAnnounceEntities(announceEntities);

                        return postContext;
                    }
                });
        AnnounceBO bizData = queryAnnouncesContext.getBizData();
        List<AnnounceEntity> userAnnounceEntities = bizData.getUserAnnounceEntity().getUserAnnounceEntities();
        return UserAnnounceVO.builder()
                .announceEntities(userAnnounceEntities)
                .more((boolean)queryAnnouncesContext.getExtra().get(Constants.NEXT_PAGE))
                .build();
    }

    @Override
    public void readAnnounce(String userId, String announceId) {
        PostContext postContext = buildPostContext(userId, announceId);
        postContext = super.doPostProcessor(postContext, ReadAnnouncePostProcessor.class, new AbstractPostProcessorOperation<ReadAnnounceEntity>() {
            @Override
            public PostContext<ReadAnnounceEntity> doMainProcessor(PostContext<ReadAnnounceEntity> postContext) {
                Integer i = repository.readAnnounce(postContext.getBizData().getUserId(), postContext.getBizData().getAnnounceId());
                postContext.addExtraData(Constants.AFFECT_LENGTH,i);
                return postContext;
            }
        });
        Integer i = (Integer)postContext.getExtra().get(Constants.AFFECT_LENGTH);
        if(i==0){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.PARAM_NOT_VALID.getCode()),GlobalServiceStatusCode.PARAM_NOT_VALID.getMessage());
        }
    }

    @Override
    public Integer queryAnnounceCountByUserId(String userId) {
        PostContext<AnnounceCountEntity> postContext = buildPostContext(userId);
        super.doPostProcessor(postContext, AnnounceCountPostProcessor.class, new AbstractPostProcessorOperation<AnnounceCountEntity>() {
            @Override
            public PostContext<AnnounceCountEntity> doMainProcessor(PostContext<AnnounceCountEntity> postContext) {
                Integer count = repository.queryAnnounceCountByUserId(postContext.getBizData().getUserId());
                postContext.getBizData().setCount(count);
                return postContext;
            }
        });
        return postContext.getBizData().getCount();
    }

    @Override
    public void readAllAnnounce(String userId) {
        PostContext postContext = buildPostContext(userId,null);
        super.doPostProcessor(postContext, ReadAllAnnouncePostProcessor.class, new AbstractPostProcessorOperation<ReadAnnounceEntity>() {
            @Override
            public PostContext<ReadAnnounceEntity> doMainProcessor(PostContext<ReadAnnounceEntity> postContext) {
                Integer row = repository.ReadAllAnnounce(postContext.getBizData().getUserId());
                postContext.addExtraData(Constants.AFFECT_LENGTH,row);
                return postContext;
            }
        });
        Integer row = (Integer)postContext.getExtra().get(Constants.AFFECT_LENGTH);
        if(row==0){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.PARAM_NOT_VALID.getCode()),GlobalServiceStatusCode.PARAM_NOT_VALID.getMessage());
        }
    }

    private static PostContext<AnnounceCountEntity> buildPostContext(String userId) {
        return PostContext.<AnnounceCountEntity>builder()
                .bizId(BizModule.ANNOUNCE.getCode())
                .bizName(BizModule.ANNOUNCE.getName())
                .bizData(AnnounceCountEntity.builder()
                        .userId(userId)
                        .build())
                .build();
    }

    private static PostContext<AnnounceBO> buildPostContext(String userId, Integer limit, String lastAnnounceId) {
        return PostContext.<AnnounceBO>builder()
                .bizId(BizModule.ANNOUNCE.getCode())
                .bizName(BizModule.ANNOUNCE.getName())
                .bizData(AnnounceBO.builder()
                        .userAnnounceEntity(UserAnnounceEntity.builder()
                                .userId(userId)
                                .build())
                        .pageResult(PageResult.builder()
                                .limit(limit)
                                .lastAnnounceId(lastAnnounceId)
                                .build())
                        .build())
                .build();
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
}
