package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.IUserRepository;
import com.achobeta.domain.login.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.DeviceMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.UserPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 用户仓储层接口
 * @Date: 2024/11/10 16:19
 * @Version: 1.0
 */

@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private UserMapper userMapper;

    /*
     * 根据手机号查询用户
     * @param phone 手机号
     * @return UserEntity 用户实体
     */
    @Override
    public UserEntity getUserByPhone(String phone){
        UserPO userPO = userMapper.getUserByPhone(phone);
        if(null == userPO){
            return null;
        }
        return UserEntity.builder()
                            .userId(userPO.getUserId())
                            .build();
    }
}
