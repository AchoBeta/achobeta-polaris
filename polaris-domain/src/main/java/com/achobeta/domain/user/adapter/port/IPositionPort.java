package com.achobeta.domain.user.adapter.port;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 个人团队职位分组外部接口
 * @date 2024/11/8
 */
public interface IPositionPort {

    List<List<String>> getUserPosition(String userId);

}
