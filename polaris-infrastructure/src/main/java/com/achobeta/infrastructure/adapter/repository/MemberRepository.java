package com.achobeta.infrastructure.adapter.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.LikeMapper;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.RoleMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.achobeta.types.support.util.BuildKeyUtil.buildOtherUserInfoKey;
import static com.achobeta.types.support.util.BuildKeyUtil.buildUserInfoKey;

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
    private LikeMapper likeMapper;

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
    public void addMember(UserEntity userEntity,String operatorId) {
        String userId = userEntity.getUserId();
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

        if (!CollectionUtil.isEmpty(userEntity.getPositionList())) {
            // 转换格式
            List<List<String>> positions = userEntity.getPositionList().stream()
                    .map(s -> Arrays.asList(s.split("-")))
                    .collect(Collectors.toList());

            // 取到团队名称团队id
            List<String> teamNames = positions.stream()
                    .map(list -> list.get(0))
                    .collect(Collectors.toList());
            if (CollectionUtil.isEmpty(teamNames)) {
                teamNames.add("未选择团队");
            }
            List<String> teamIds = positionMapper.listTeamIdByNames(teamNames);
            // 给用户添加团队
            userMapper.addMemberTeam(userId, teamIds, operatorId);

            // 添加职位
            // 只到团队的也要加
            List<List<String>> positionOnlyTeamNames = positions.stream()
                    .filter(list -> list.size() == 1)
                    .collect(Collectors.toList());
            List<List<String>> positionNames = positions.stream()
                    .filter(list -> list.size() >= 2)
                    .collect(Collectors.toList());
            // 如果在团队下有职位，就不用单独加团队的
            List<String> positionInTeamNames = positionNames.stream()
                    .map(list -> list.get(0))
                    .collect(Collectors.toList());
            for (List<String> position : positionOnlyTeamNames) {
                if (positionInTeamNames.contains(position.get(0))) {
                    continue;
                }
                List<String> temp = new ArrayList<>();
                temp.add(position.get(0));
                temp.add(position.get(0));
                positionNames.add(temp);
            }

            // 添加职位
            List<PositionEntity> addPositions = new ArrayList<>();
            if (!CollectionUtil.isEmpty(positionNames)) {
                // 拿到每个职位的团队名称
                List<String> addPositionTeamNames = positionNames.stream()
                        .map(list -> list.get(0))
                        .collect(Collectors.toList());
                List<String> addPositionNames = positionNames.stream()
                        .map(list -> list.get(list.size() - 1))
                        .collect(Collectors.toList());
                for (int i = 0; i < addPositionNames.size(); i++) {
                    addPositions.add(PositionEntity.builder()
                            .positionName(addPositionNames.get(i))
                            .teamName(addPositionTeamNames.get(i))
                            .build());
                }
                addPositions = positionMapper.listPositionIdAndTeamIdByNames(addPositions);
                if (!CollectionUtil.isEmpty(addPositions)){
                    positionMapper.addPositionToUser(addPositions, userId, operatorId);
                }
            }
        }

        // 修改用户角色
        List<String> roleNames = userEntity.getRoles();
        if (!CollectionUtil.isEmpty(roleNames)) {
            List<String> roleIds = roleMapper.listRoleIdsByNames(roleNames);
            roleMapper.addUserRoles(userId, roleIds);
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
                .userId(userId)
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
                .liked(Optional.ofNullable(likeMapper.queryLikedById(userId, userId)).map(i -> i == 1).orElse(false))
                .positions(positionNames)
                .roles(roleMapper.listRoleNamesByUserId(userId))
                .build();

        return userEntity;
    }

    @Override
    public UserEntity modifyMemberInfo(UserEntity userEntity, String teamId, String operatorId) {
        String userId = userEntity.getUserId();
        // 这里保证用户存在
        UserPO userPO = userMapper.getUserByUserId(userId);
        if (userPO == null) {
            log.error("用户不存在！userId：{}",userId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()),
                    GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }

        if (!CollectionUtil.isEmpty(userEntity.getPositionList())) {
            // 转换格式
            List<List<String>> positions = userEntity.getPositionList().stream()
                    .map(s -> Arrays.asList(s.split("-")))
                    .collect(Collectors.toList());
            // 取到团队名称团队id
            List<String> teamNames = positions.stream()
                    .map(list -> list.get(0))
                    .collect(Collectors.toList());
            if (CollectionUtil.isEmpty(teamNames)) {
                teamNames.add("未选择团队");
            }
            List<String> teamIds = positionMapper.listTeamIdByNames(teamNames);
            // 修改用户所属团队，先全部删掉，再添加
            userMapper.deleteMemberTeam(userId, operatorId);
            userMapper.addMemberTeam(userId, teamIds, operatorId);

            positionMapper.deletePositionByUserId(userId);
            // 只到团队的也要加
            List<List<String>> positionOnlyTeamNames = positions.stream()
                    .filter(list -> list.size() == 1)
                    .collect(Collectors.toList());
            List<List<String>> positionNames = positions.stream()
                    .filter(list -> list.size() >= 2)
                    .collect(Collectors.toList());
            // 如果在团队下有职位，就不用单独加团队的
            List<String> positionInTeamNames = positionNames.stream()
                    .map(list -> list.get(0))
                    .collect(Collectors.toList());
            for (List<String> position : positionOnlyTeamNames) {
                if (positionInTeamNames.contains(position.get(0))) {
                    continue;
                }
                List<String> temp = new ArrayList<>();
                temp.add(position.get(0));
                temp.add(position.get(0));
                positionNames.add(temp);
            }

            // 添加职位
            List<PositionEntity> addPositions = new ArrayList<>();
            if (!CollectionUtil.isEmpty(positionNames)) {
                // 拿到每个职位的团队名称
                List<String> addPositionTeamNames = positionNames.stream()
                        .map(list -> list.get(0))
                        .collect(Collectors.toList());
                List<String> addPositionNames = positionNames.stream()
                        .map(list -> list.get(list.size() - 1))
                        .collect(Collectors.toList());
                for (int i = 0; i < addPositionNames.size(); i++) {
                    addPositions.add(PositionEntity.builder()
                            .positionName(addPositionNames.get(i))
                            .teamName(addPositionTeamNames.get(i))
                            .build());
                }
                addPositions = positionMapper.listPositionIdAndTeamIdByNames(addPositions);
                if (!CollectionUtil.isEmpty(addPositions)){
                    positionMapper.addPositionToUser(addPositions, userId, operatorId);
                }
            }
        }

        // 修改用户角色，全删再添加
        if (!Objects.equals(operatorId, userId)) {
            roleMapper.deleteUserRoles(userId);
            List<String> roleNames = userEntity.getRoles();
            if (!CollectionUtil.isEmpty(roleNames)) {
                List<String> roleIds = roleMapper.listRoleIdsByNames(roleNames);
                roleMapper.addUserRoles(userId, roleIds);
            }
        }

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
                .updateBy(operatorId)
                .build());

        redisService.remove(buildUserInfoKey(userId));
        return userEntity;
    }

    @Override
    public UserEntity queryMemberInfo(String userId, String memberId) {
        UserEntity userBaseInfo = redisService.getValue(buildOtherUserInfoKey(userId, memberId));
        if(userBaseInfo!= null) {
            return userBaseInfo;
        }

        UserPO userPO = userMapper.getUserByUserId(memberId);
        if(userPO == null) {
            log.error("用户不存在！userId：{}",memberId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()),
                    GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }

        // 封装position信息，先存根节点
        List<List<PositionPO>> positionPOList = new ArrayList<>();
        List<PositionPO> listPositions = positionMapper.listPositionByUserId(memberId);
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
        log.info("从数据库中查询用户信息成功，userId: {}",memberId);
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
                .liked(Optional.ofNullable(likeMapper.queryLikedById(userId, memberId)).map(i -> i == 1).orElse(false))
                .positions(positionNames)
                .roles(roleMapper.listRoleNamesByUserId(memberId))
                .build();

        redisService.setValue(buildOtherUserInfoKey(userId, memberId),userEntity);
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
            UserEntity userEntity = redisService.getValue(buildUserInfoKey(userId));
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
                    .liked(Optional.ofNullable(likeMapper.queryLikedById(userId, userPO.getUserId())).map(i -> i == 1).orElse(false))
                    .positions(positionNames)
                    .roles(roleMapper.listRoleNamesByUserId(userId))
                    .build();
            // 存入redis
            redisService.setValue(buildUserInfoKey(userId), userEntity);

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
