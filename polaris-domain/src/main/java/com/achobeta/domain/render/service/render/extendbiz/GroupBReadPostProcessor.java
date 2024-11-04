package com.achobeta.domain.render.service.render.extendbiz;

import com.achobeta.domain.render.adapter.port.IAuthPort;
import com.achobeta.domain.render.model.bo.RenderBO;
import com.achobeta.domain.render.model.entity.BookEntity;
import com.achobeta.domain.render.model.entity.UserEntity;
import com.achobeta.domain.render.service.render.RenderBookPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import com.achobeta.types.support.util.PatternStrUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
@Component
public class GroupBReadPostProcessor implements RenderBookPostProcessor {

    @Resource
    private IAuthPort authPort;

    @Override
    public void handleAfter(PostContext<RenderBO> postContext) {
        RenderBO renderBO = postContext.getBizData();
        UserEntity userEntity = renderBO.getUserEntity();
        BookEntity bookEntity = renderBO.getBookEntity();
        userEntity = authPort.queryUserAuth(userEntity.getUserId()).getUserEntity();
        if ("AchoBeta5.0".equals(userEntity.getDepartment())) {
            String text = PatternStrUtil.replaceText(bookEntity.getBookContent(),
                    "<font color=\"red\">", "</font>");
            bookEntity.setBookContent(text);
            postContext.setBizData(renderBO);
        }
    }

}
