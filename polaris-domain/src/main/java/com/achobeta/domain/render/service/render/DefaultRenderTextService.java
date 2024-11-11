package com.achobeta.domain.render.service.render;

import com.achobeta.domain.render.adapter.repository.IBookRepository;
import com.achobeta.domain.render.model.bo.RenderBO;
import com.achobeta.domain.render.model.entity.BookEntity;
import com.achobeta.domain.render.model.entity.UserEntity;
import com.achobeta.domain.render.model.valobj.RenderBookVO;
import com.achobeta.domain.render.service.IRenderTextService;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author BanTanger 半糖
 * @description 默认文本渲染服务实现类，如果要重写渲染实现不应该直接修改本类，
 * 而是通过新定义一个 xxxRenderText 继承 IRenderTextService
 * @date 2024/11/4
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRenderTextService extends AbstractPostProcessor<RenderBO> implements IRenderTextService {

    //    private final IAuthPort authPort;
    private final IBookRepository repository;

    @Override
    public RenderBookVO renderBook(String userId, String bookId) {
        PostContext<RenderBO> renderContext = buildPostContext(userId, bookId);
        renderContext = super.doPostProcessor(renderContext, RenderBookPostProcessor.class);
        return RenderBookVO.builder()
                .userName(renderContext.getBizData().getUserEntity().getUserName())
                .bookName(renderContext.getBizData().getBookEntity().getBookName())
                .bookContent(renderContext.getBizData().getBookEntity().getBookContent())
                .build();
    }

    @Override
    public PostContext<RenderBO> doMainProcessor(PostContext<RenderBO> postContext) {
        RenderBO renderBO = postContext.getBizData();
        UserEntity userEntity = renderBO.getUserEntity();
        BookEntity bookEntity = renderBO.getBookEntity();

        bookEntity = repository.queryBook(bookEntity.getBookId());

        postContext.setBizData(RenderBO.builder().userEntity(userEntity).bookEntity(bookEntity).build());
        return postContext;
    }

    @Override
    public PostContext<RenderBO> doInterruptMainProcessor(PostContext<RenderBO> postContext) {
        log.info("重写前置校验中断主流程逻辑方法 ... ");
        return postContext;
    }

    private static PostContext<RenderBO> buildPostContext(String userId, String bookId) {
        return PostContext.<RenderBO>builder()
            .bizId(BizModule.RENDER.getCode())
            .bizName(BizModule.RENDER.getName())
            .bizData(RenderBO.builder()
                .bookEntity(BookEntity.builder().bookId(bookId).build())
                .userEntity(UserEntity.builder().userId(userId).build())
                .build())
            .build();
    }

}
