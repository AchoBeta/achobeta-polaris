package com.achobeta.infrastructure.adapter.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
    private RedissonService redisService;

    @Override
    public List<UserEntity> queryMemberList(String teamId, String lastUserId, Integer limit) {
        List<UserEntity> members = new ArrayList<>();

        Long lastId = lastUserId==null || lastUserId.isEmpty() ? 0 : userMapper.getIdByUserId(lastUserId);
        List<UserPO> userPOList = userMapper.listMemberByTeamId(teamId, lastId, limit);
        for (UserPO userPO : userPOList) {
            String userId = userPO.getUserId();
            // 从redis中获取用户信息
            UserEntity userEntity = redisService.getValue(Constants.USER_INFO + userId);
            if (userEntity!= null) {
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
            members.add(userEntity);
            // 存入redis
            redisService.setValue(Constants.USER_INFO + userId, userEntity);
        }
        return members;
    }

}
