package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.IUserRepository;
import com.achobeta.domain.login.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.DeviceMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.UserPO;
import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.RedisKey;
import com.achobeta.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.achobeta.types.enums.GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST;

/**
 * @author yangzhiyao
 * @description 用户仓储接口实现类
 * @date 2024/11/6
 */
@Slf4j
@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private IRedisService redisService;

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

    @Override
    public void updateUserInfo(UserEntity userEntity) {
        log.info("查询用户是否存在，userId: {}",userEntity.getUserId());
        // 这里保证用户存在，不能查缓存的得直接查数据库的
        UserPO userPO = userMapper.getUserByUserId(userEntity.getUserId());
        if (userPO == null) {
            log.error("用户不存在！userId：{}",userEntity.getUserId());
            throw new AppException(String.valueOf(USER_ACCOUNT_NOT_EXIST.getCode()),
                    USER_ACCOUNT_NOT_EXIST.getMessage());
        }

        log.info("清除用户缓存信息，userId: {}",userEntity.getUserId());
        redisService.remove(RedisKey.USER_INFO + userEntity.getUserId());

        log.info("更新用户信息：{}",userEntity.getUserId());
         userMapper.updateUserInfo(UserPO.builder()
                 .userId(userEntity.getUserId())
                 .userName(userEntity.getUserName())
                 .phone(userEntity.getPhone())
                 .gender(userEntity.getGender())
                 .idCard(userEntity.getIdCard())
                 .email(userEntity.getEmail())
                 .grade(userEntity.getGrade())
                 .major(userEntity.getMajor())
                 .studentId(userEntity.getStudentId())
                 .experience(userEntity.getExperience())
                 .currentStatus(userEntity.getCurrentStatus())
                 .entryTime(userEntity.getEntryTime())
                 .likeCount(userEntity.getLikeCount())
                 .updateBy(userEntity.getUserId())
                 .build());
        log.info("更新用户信息成功，userId: {}",userEntity.getUserId());
    }


    @Override
    public UserEntity queryUserInfo(String userId) {
        log.info("尝试从redis中获取用户信息，userId: {}",userId);
        UserEntity userBaseInfo = redisService.getValue(RedisKey.USER_INFO + userId);
        if(userBaseInfo!= null) {
            return userBaseInfo;
        }

        log.info("从数据库中查询用户信息，userId: {}",userId);
        UserPO userPO = userMapper.getUserByUserId(userId);
        if(userPO == null) {
            log.error("用户不存在！userId：{}",userId);
            throw new AppException(String.valueOf(USER_ACCOUNT_NOT_EXIST.getCode()),
                    USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        // 封装position信息，先存根节点
        List<List<PositionPO>> positionPOList = new ArrayList<>();
        List<PositionPO> listPositions = positionMapper.listPositionByUserId(userId);
        for(PositionPO rootPosition : listPositions) {
            List<PositionPO> tempList = new ArrayList<>();
            tempList.add(rootPosition);
            positionPOList.add(tempList);
        }
        // 顺序获取父节点，并父子节点添加到根节点的children中
        if (!CollectionUtil.isEmpty(listPositions)) {
            listPositions = positionMapper.listParentPositionByPositions(listPositions);
        }
        while(!CollectionUtil.isEmpty(listPositions)) {
            for (PositionPO positionPO : listPositions) {
                for (List<PositionPO> positionList : positionPOList) {
                    if (positionList.get(positionList.size() - 1)
                            .getPositionId()
                            .equals(positionPO.getSubordinate())) {
                        positionList.add(positionPO);
                        break;
                    }
                }
            }
            listPositions = positionMapper.listParentPositionByPositions(listPositions);
        }
        // 单取职位名称
        List<List<String>> positionNames = new ArrayList<>();
        for(List<PositionPO> positionList : positionPOList) {
            List<String> tempList = new ArrayList<>();
            tempList.add(positionList.get(0).getPositionId());
            for(int i = positionList.size() - 1; i >= 0; i--) {
                tempList.add(positionList.get(i).getPositionName());
            }
            positionNames.add(tempList);
        }
        log.info("从数据库中查询用户信息成功，userId: {}",userId);
        // TODO:待添加获取用户点赞状态
        UserEntity userEntity = UserEntity.builder()
                .userId(userPO.getUserId())
                .userName(userPO.getUserName())
                .phone(userPO.getPhone())
                .gender(userPO.getGender())
                .idCard(userPO.getIdCard())
                .email(userPO.getEmail())
                .grade(userPO.getGrade())
                .major(userPO.getMajor())
                .studentId(userPO.getStudentId())
                .experience(userPO.getExperience())
                .currentStatus(userPO.getCurrentStatus())
                .entryTime(userPO.getEntryTime())
                .likeCount(userPO.getLikeCount())
                .liked(false)
                .positions(positionNames)
                .build();

        log.info("将用户信息缓存到redis，userId: {}",userId);
        redisService.setValue(RedisKey.USER_INFO + userId,userEntity);
        log.info("将用户信息缓存到redis成功，userId: {}",userId);
        return userEntity;
    }

}
