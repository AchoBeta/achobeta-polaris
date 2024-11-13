package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.PositionPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位/分组dao
 * @date 2024/11/7
 */
@Mapper
public interface PositionMapper {
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
     * 获取某个用户的所有最下级职位
     * @param userId
     * @return
     */
    List<PositionPO> listPositionByUserId(String userId);

    /**
     * 添加职位/分组
     * @param positionPOList
     */
    void addPosition(List<PositionPO> positionPOList);

    /**
     * 在position表中删除职位/分组
     * @param positionPOList
     */
    void deletePositionInPosition(List<PositionPO> positionPOList);

    /**
     * 在user_position关联表中删除职位/分组
     * @param positionPOList
     */
    void deletePositionInUserPosition(List<PositionPO>positionPOList);
}
