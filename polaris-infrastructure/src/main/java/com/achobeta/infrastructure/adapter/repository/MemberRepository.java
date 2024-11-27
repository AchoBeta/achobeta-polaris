package com.achobeta.infrastructure.adapter.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.RoleMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.RedisKey;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 团队成员仓储接口
 * @date 2024/11/17
 */
@Slf4j
@Repository
public class MemberRepository implements IMemberRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private IRedisService redisService;

    @Override
    public void deleteMember(String userId, String memberId, String teamId) {

        log.info("成员表中删除成员，userId:{}, memberId:{}", userId, memberId);
        userMapper.deleteMember(userId, memberId, teamId);

        log.info("用户职位关联表删除关联数据，userId:{}, memberId:{}，teamId:{}", userId, memberId, teamId);
        userMapper.deleteUserPosition(userId, memberId, teamId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMember(UserEntity userEntity,String userId, String teamId, List<String> positionIds) {
        userMapper.addUser(UserPO.builder()
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
                .build());
        userMapper.addMember(userEntity.getUserId(), teamId);
        roleMapper.addUserRoles(userId, userEntity.getRoles());
        if (!CollectionUtil.isEmpty(positionIds)) {
            positionMapper.addPositionsToMember(userId, userEntity.getUserId(), positionIds, teamId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserEntity queryMemberByPhone(String phone) {
        log.info("从数据库中查询用户信息，phone: {}",phone);
        UserPO userPO = userMapper.getMemberByPhone(phone);
        if(userPO == null) {
            return null;
        }
        String userId = userPO.getUserId();
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

        return userEntity;
    }

    @Override
    public UserEntity modifyMemberInfo(UserEntity userEntity, String teamId, List<String> addPositions, List<String> deletePositions) {
        String userId = userEntity.getUserId();
        // 这里保证用户存在，不能查缓存的得直接查数据库的
        UserPO userPO = userMapper.getUserByUserId(userId);
        if (userPO == null) {
            log.error("用户不存在！userId：{}",userId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()),
                    GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }

        if (!CollectionUtil.isEmpty(addPositions)) {
            positionMapper.addPositionToUser(addPositions, userId, teamId);
        }
        if (!CollectionUtil.isEmpty(deletePositions)) {
            positionMapper.deletePositionWithUser(deletePositions, userId);
        }

        roleMapper.deleteUserRoles(userId);
        roleMapper.addUserRoles(userId, userEntity.getRoles());
        userMapper.updateMemberInfo(UserPO.builder()
                .userId(userId)
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

        redisService.remove(RedisKey.USER_INFO + userId);
        return userEntity;
    }

    @Override
    public UserEntity queryMemberInfo(String userId) {
        log.info("尝试从redis中获取用户信息，userId: {}",userId);
        UserEntity userBaseInfo = redisService.getValue(RedisKey.USER_INFO + userId);
        if(userBaseInfo!= null) {
            return userBaseInfo;
        }

        log.info("从数据库中查询用户信息，userId: {}",userId);
        UserPO userPO = userMapper.getUserByUserId(userId);
        if(userPO == null) {
            log.error("用户不存在！userId：{}",userId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()),
                    GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
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

    @Override
    public List<UserEntity> queryMemberList(String teamId, String lastUserId, Integer limit) {
        List<UserEntity> members = new ArrayList<>();

        Long lastId = StringUtil.isEmpty(lastUserId) ? 0 : userMapper.getIdByUserId(lastUserId);
        List<UserPO> userPOList = userMapper.listMemberByTeamId(teamId, lastId, limit);
        for (UserPO userPO : userPOList) {
            String userId = userPO.getUserId();
            // 从redis中获取用户信息
            UserEntity userEntity = redisService.getValue(RedisKey.USER_INFO + userId);
            if (userEntity!= null) {
                userEntity = convertToMemberListPositionNames(userEntity);
                members.add(userEntity);
                continue;
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
            // 单取职位名称，包括团队名称
            List<List<String>> positionNames = new ArrayList<>();
            for(List<PositionPO> positionList : positionPOList) {
                List<String> tempList = new ArrayList<>();
                // 第一个元素为positionId，便于在修改时定位到该职位发送请求
                tempList.add(positionList.get(0).getPositionId());
                for(int i = positionList.size() - 1; i >= 0; i--) {
                    tempList.add(positionList.get(i).getPositionName());
                }
                positionNames.add(tempList);
            }
            userEntity = UserEntity.builder()
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
            // 存入redis
            redisService.setValue(RedisKey.USER_INFO + userId, userEntity);

            // 转换positionNames格式，便于前端选择显示
            userEntity = convertToMemberListPositionNames(userEntity);
            members.add(userEntity);
        }
        return members;
    }

    private static UserEntity convertToMemberListPositionNames(UserEntity userEntity) {
        List<List<String>> positionNames = userEntity.getPositions();
        List<String> resultNameList = new ArrayList<>();
        for (List<String> positionList : positionNames) {
            List<String> tempList = new ArrayList<>();
            // 前两个元素为positionId和团队名称，这里要转换成成员列表的职位显示格式，所以下标从2开始
            for (int i = 2; i < positionList.size(); i++) {
                tempList.add(positionList.get(i));
            }
            resultNameList.add(String.join("-", tempList));
        }
        userEntity.setPositionList(resultNameList);
        return userEntity;
    }

}
