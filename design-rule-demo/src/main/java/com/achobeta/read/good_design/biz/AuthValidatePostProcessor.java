package com.achobeta.read.good_design.biz;

import com.achobeta.read.AuthHolder;
import com.achobeta.read.good_design.ReaderBO;
import com.achobeta.read.good_design.TextReadPostProcessor;
import com.achobeta.read.good_design.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chensongmin
 * @description 权限校验扩展点实现
 * 无论什么业务场景，权限校验在主流程前，优先级直接定义成最小
 * @create 2024/11/3
 */
@Slf4j
@Component
public class AuthValidatePostProcessor implements TextReadPostProcessor {

    @Override
    public boolean handleBefore(PostContext<ReaderBO> postContext) {
        ReaderBO readerBO = postContext.getBizData();
        if (!AuthHolder.authGroup(readerBO.getUser()) && !AuthHolder.isInWhiteList(readerBO.getUser())) {
            log.error("鉴权失败! 部门 " + readerBO.getUser().getName() + " 暂无访问 read 方法权限!");
            // 干预主流程继续执行
            return false;
        }
        // 不干预主流程继续执行
        return true;
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}
