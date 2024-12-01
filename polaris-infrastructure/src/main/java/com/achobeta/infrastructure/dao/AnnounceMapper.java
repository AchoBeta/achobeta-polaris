package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.AnnouncePO;
import com.achobeta.infrastructure.dao.po.AnnounceReciverPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangwenxing
 * @description 公告Dao接口
 * @data 2024/11/7
 * */
@Mapper
public interface AnnounceMapper {
    /**
     *
     * @param announceId
     * @param
     * @return 公告持久化对象
     */
    List<AnnouncePO> queryAnnouncesByAnnounceId(@Param("announceIds")List<String> announceId);

    /**
     *
     * @param userId
     * @return 公告接收者持久化对象
     */
    List<AnnounceReciverPO> queryReadByUserIdAnnounceId(@Param("userId")String userId,@Param("limit")Integer limit, @Param("announceId")String announceId);

    /**
     *
     * @param userId
     * @param announceId
     * @return 受影响行数
     */
    Integer readAnnounce(@Param("userId")String userId,@Param("announceId")String announceId);

    /**
     *
     * @param userId
     * @return 用户公告数量
     */
    Integer getAnnounceCount(@Param("userId")String userId);
    Integer readAllAnnounce(@Param("userId")String userId);

}
