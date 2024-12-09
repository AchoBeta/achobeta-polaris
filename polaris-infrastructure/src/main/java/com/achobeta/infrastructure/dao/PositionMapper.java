package com.achobeta.infrastructure.dao;

import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.infrastructure.dao.po.PositionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    void addPositionsToMember(@Param("userId")String userId,@Param("memberId") String memberId, @Param("positionIds")List<String> positionIds, String teamId);

    /**
     * 给用户添加职位
     * @param positions
     * @param userId
     */
    void addPositionToUser(@Param("positions")List<PositionEntity> positions,@Param("userId")  String userId,@Param("operatorId") String operatorId);

    /**
     * 删除用户的职位
     * @param positions
     * @param userId
     */
    void deletePositionWithUser(@Param("positions")List<PositionEntity> positions,@Param("userId") String userId);

    /**
     * 获取某个职位的所有下属职位
     * @param positionId
     * @return
     */
    List<PositionPO> listSubordinateByPositionId(@Param("positionId") String positionId);

    /**
     * 获取某个职位的信息
     * @param positionId
     * @return
     */
    PositionPO getPositionByPositionId(@Param("positionId") String positionId);

    /**
     * 获取某个职位的上级职位
     * 用以查询用户的个人职位
     * @param positionId
     * @return
     */
    PositionPO getParentPositionByPositionId(@Param("positionId") String positionId);

    /**
     * 添加职位/分组
     * @param positionPOList
     */
    void addPosition(@Param("positionPOList")List<PositionPO> positionPOList);

    /**
     * 在position表中删除职位/分组
     * @param positionsToDelete
     */
    void deletePositionInPosition(@Param("positionsToDelete")Collection<String> positionsToDelete);

    /**
     * 在user_position关联表中删除职位/分组
     * @param positionsToDelete
     */
    void deletePositionInUserPosition(@Param("positionsToDelete")Collection<String> positionsToDelete);

    /**
     * 用于在删除节点时查询要被删除的节点关联的所有用户
     * @param positionIds
     * @return 对应的所有用户id
     */
    List<String> listUserIdsByPositionIds(@Param("positionIds")Collection<String> positionIds);

    /**
     * 在删除职位时将用户关联到根（父）节点
     * @param positionId 根（父）节点
     * @param userIds 需要更改职位的用户
     */
    void addUsersToPosition(@Param("positionId")String positionId,@Param("userIds") List<String> userIds);
  
    /**
     * 获取某个职位的上级职位
     * 用以查询用户的个人职位
     * @param positions
     * @return
     */
    List<PositionPO> listParentPositionByPositions(@Param("positions")Collection<PositionPO> positions);

    /**
     * 获取某个用户的所有最下级职位
     * @param userId
     * @return
     */
    List<PositionPO> listPositionByUserId(@Param("userId") String userId);

    /**
     * 获取某个用户的团队Id和名称
     * @param userId
     * @return
     */
    List<PositionPO> listTeamByUserId(@Param("userId") String userId);

    /**
     * 根据职位名称和团队名称获取职位Id和团队Id
     * @param positions
     * @return
     */
    List<PositionEntity> listPositionIdAndTeamIdByNames(@Param("positions")List<PositionEntity> positions);

    /**
     * 根据团队名称获取团队Id
     * @param teamNames
     * @return
     */
    List<String> listTeamIdByNames(@Param("teamNames")List<String> teamNames);

    /**
     * 根据用户Id删除用户的职位
     * @param userId
     */
    void deletePositionByUserId(@Param("userId") String userId);
}