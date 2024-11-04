package com.achobeta.domain.render.service.render.extendbiz;

import com.achobeta.domain.render.adapter.port.IAuthPort;
import com.achobeta.domain.render.model.bo.RenderBO;
import com.achobeta.domain.render.model.entity.BookEntity;
import com.achobeta.domain.render.model.entity.UserEntity;
import com.achobeta.domain.render.service.render.RenderBookPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chensongmin
 * @description 白名单校验扩展实现
 * 这里隐藏的一个逻辑是白名单优先级一定是低于用户组的优先级的，因此需要重写优先级
 * @create 2024/11/3
 */
@Slf4j
@Component
public class WhiteListAuthPostProcessor implements RenderBookPostProcessor {

    @Resource
    private IAuthPort authPort;

    @Override
    public void handleAfter(PostContext<RenderBO> postContext) {
        RenderBO renderBO = postContext.getBizData();
        UserEntity userEntity = renderBO.getUserEntity();
        BookEntity bookEntity = renderBO.getBookEntity();
        if (authPort.queryUserAuth(userEntity.getUserId()).getIsInWhite()) {
            // "这是 AchoBeta Polaris 北极星系统".length() = 25
            String text = bookEntity.getBookContent().substring(0, 25) + "... 【想看更多内容，请关注 AchoBeta！】";
            bookEntity.setBookContent(text);
            postContext.setBizData(renderBO);
        }
    }

    @Override
    public int getPriority() {
        return -1;
    }
}
