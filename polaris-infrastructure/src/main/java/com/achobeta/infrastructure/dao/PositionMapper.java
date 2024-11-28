package com.achobeta.infrastructure.dao;

import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.infrastructure.dao.po.PositionPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位/分组dao
 * @date 2024/11/7
 */
@Mapper
public interface PositionMapper {

    /**
     * 给成员添加职位
     * @param userId 操作者ID
     * @param memberId 成员ID
     * @param positionIds 职位ID列表
     */
    void addPositionsToMember(String userId, String memberId, List<String> positionIds, String teamId);

    /**
     * 给用户添加职位
     * @param positions
     * @param userId
     */
    void addPositionToUser(List<PositionEntity> positions, String userId);

    /**
     * 删除用户的职位
     * @param positions
     * @param userId
     */
    void deletePositionWithUser(List<PositionEntity> positions, String userId);

    /**
     * 获取某个职位的所有下属职位
     * @param positionId
     * @return
     */
    List<PositionPO> listSubordinateByPositionId(String positionId);

    /**
     * 获取某个职位的信息
     * @param positionId
     * @return
     */
    PositionPO getPositionByPositionId(String positionId);

    /**
     * 获取某个职位的上级职位
     * 用以查询用户的个人职位
     * @param positionId
     * @return
     */
    PositionPO getParentPositionByPositionId(String positionId);

    /**
     * 添加职位/分组
     * @param positionPOList
     */
    void addPosition(List<PositionPO> positionPOList);

    /**
     * 在position表中删除职位/分组
     * @param positionsToDelete
     */
    void deletePositionInPosition(Collection<String> positionsToDelete);

    /**
     * 在user_position关联表中删除职位/分组
     * @param positionsToDelete
     */
    void deletePositionInUserPosition(Collection<String> positionsToDelete);

    /**
     * 用于在删除节点时查询要被删除的节点关联的所有用户
     * @param positionIds
     * @return 对应的所有用户id
     */
    List<String> listUserIdsByPositionIds(Collection<String> positionIds);

    /**
     * 在删除职位时将用户关联到根（父）节点
     * @param positionId 根（父）节点
     * @param userIds 需要更改职位的用户
     */
    void addUsersToPosition(String positionId, List<String> userIds);
  
    /**
     * 获取某个职位的上级职位
     * 用以查询用户的个人职位
     * @param positions
     * @return
     */
    List<PositionPO> listParentPositionByPositions(Collection<PositionPO> positions);

    /**
     * 获取某个用户的所有最下级职位
     * @param userId
     * @return
     */
    List<PositionPO> listPositionByUserId(String userId);

    /**
     * 获取某个用户的团队Id和名称
     * @param userId
     * @return
     */
    List<PositionPO> listTeamByUserId(String userId);

    /**
     * 根据职位名称和团队名称获取职位Id和团队Id
     * @param Positions
     * @return
     */
    List<PositionEntity> listPositionIdAndTeamIdByNames(List<PositionEntity> Positions);

    /**
     * 根据团队名称获取团队Id
     * @param teamNames
     * @return
     */
    List<String> listTeamIdByNames(List<String> teamNames);
}