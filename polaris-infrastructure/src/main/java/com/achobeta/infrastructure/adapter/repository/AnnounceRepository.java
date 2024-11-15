package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.announce.adapter.repository.IAnnounceRepository;
import com.achobeta.domain.announce.model.entity.AnnounceEntity;
import com.achobeta.infrastructure.dao.AnnounceMapper;
import com.achobeta.infrastructure.dao.po.AnnouncePO;
import com.achobeta.infrastructure.dao.po.AnnounceReciverPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangwenxing
 * @description
 * @date 2024/11/9
 */
@Repository
@Slf4j
public class AnnounceRepository implements IAnnounceRepository {
    @Resource
    private AnnounceMapper announceMapper;
    @Override
    public List<AnnounceEntity> queryAnnouncesByUserId(String userId,Integer limit,String lastAnnounceId) {
        //只有已读状态和公告id的公告接收者PO
        List<AnnounceReciverPO> announceReciverPOS = announceMapper.queryReadByUserIdAnnounceId(userId,limit,lastAnnounceId);
        //将公告接收者PO转变为公告实体类，并放入map中，方便接下来为公告实体赋值key:公告id,value:公告实体
        Map<String, AnnounceEntity> announceEntityMap = announceReciverPOS.stream()
                .map(announceReciverPO -> AnnounceEntity.builder()
                        .read(announceReciverPO.isRead())
                        .announceId(announceReciverPO.getAnnounceId())
                        .build())
                .collect(Collectors.toMap(
                        AnnounceEntity::getAnnounceId,
                        entity -> entity
                ));
        //将公告id放到集合中，提高查询效率
        List<String> announceIds = announceReciverPOS.stream().map(AnnounceReciverPO::getAnnounceId).collect(Collectors.toList());
        //获取具体公告PO
        List<AnnouncePO> announcePOS = announceMapper.queryAnnouncesByAnnounceId(announceIds);
        // 遍历announcePOS，更新announceEntityMap中的对象
        for (AnnouncePO announcePO : announcePOS) {
            String announceId = announcePO.getAnnounceId();
            AnnounceEntity announceEntity = announceEntityMap.get(announceId);
            if (announceEntity != null) {
                // 更新AnnounceEntity的属性
                announceEntity.setTitle(announcePO.getTitle());
                announceEntity.setBody(announcePO.getBody());
                announceEntity.setType(announcePO.getType());
            }
        }
        return new ArrayList<>(announceEntityMap.values());
    }

    @Override
    public Integer readAnnounce(String userId, String announceId) {
        return announceMapper.readAnnounce(userId, announceId);
    }
}
