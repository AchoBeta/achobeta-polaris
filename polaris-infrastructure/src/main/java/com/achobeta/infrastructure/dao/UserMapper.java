package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2024/11/10 16:28
 * @Version: 1.0
 */

@Mapper
public interface UserMapper {

    UserPO getUserByPhone(String phone);

}
