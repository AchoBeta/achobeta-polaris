package com.achobeta.domain.user.service;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description 用户修改个人信息服务统一接口
 */
public interface IModifyUserInfoService {

    void modifyUserInfo(String userId, String userName, String phone,
                        Byte gender, String idCard, String email, Integer grade,
                        String major, String studentId, String experience,
                        String currentStatus, LocalDateTime entryTime);

}
