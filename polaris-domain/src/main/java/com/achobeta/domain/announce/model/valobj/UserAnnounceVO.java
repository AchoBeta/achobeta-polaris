package com.achobeta.domain.announce.model.valobj;

import com.achobeta.domain.announce.model.entity.AnnounceEntity;
import lombok.*;

import java.util.List;

/**
 * @author huangwenxing
 * @description 用户公告VO
 * @data 2024/11/10
 * * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnnounceVO {
    /**用户公告*/
    private List<AnnounceEntity> announceEntities;
    /**是否拥有下一页*/
    private boolean more;
}