package com.achobeta.domain.user.service;

import com.achobeta.domain.user.model.entity.UserEntity;

/**
 * @author yangzhiyao
 * @description 用户修改个人信息服务统一接口
 */
public interface IModifyUserInfoService {

    void modifyUserInfo(UserEntity userEntity);

}
