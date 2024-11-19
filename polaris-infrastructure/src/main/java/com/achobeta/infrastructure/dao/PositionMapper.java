package com.achobeta.infrastructure.dao;

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
    void addPositionsToMember(String userId, String memberId, List<String> positionIds);

    /**
     * 获取职位的上级职位
     * 用以查询用户的个人职位
     * @param positions
     * @return
     */
    List<PositionPO> listParentPositionByPositions(Collection<PositionPO> positions);

    /**
     * 获取某个用户的职位列表
     * @param userId
     * @return
     */
    List<PositionPO> listPositionByUserId(String userId);

}