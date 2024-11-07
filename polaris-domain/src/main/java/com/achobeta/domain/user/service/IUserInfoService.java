package com.achobeta.domain.user.service;

import com.achobeta.domain.user.model.entity.UserEntity;

/**
 * @author yangzhiyao
 * @description 用户个人信息服务统一接口
 * @create 2024/11/6
 */
public interface IUserInfoService {

    UserEntity getUserInfo(String userId);

}
