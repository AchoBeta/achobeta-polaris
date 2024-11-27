package com.achobeta.domain.announce.model.bo;

import com.achobeta.domain.announce.model.entity.PageResult;
import com.achobeta.domain.announce.model.entity.UserAnnounceEntity;
import lombok.*;

/**
 * @author huangwenxing
 * @description 数据传输
 * @date 2024/11/11
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AnnounceBO {
    /**单页数据实体*/
    private PageResult pageResult;
    /**用户公告实体*/
    private UserAnnounceEntity userAnnounceEntity;
}
