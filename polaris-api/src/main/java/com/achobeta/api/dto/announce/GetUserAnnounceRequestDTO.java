package com.achobeta.api.dto.announce;

import lombok.*;

/**
 * @author huangwenxing
 * @date 2024/11/11
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserAnnounceRequestDTO {
    /**用户id*/
    private String userId;
    /**单页长度*/
    private int limit;
    /**单页最后一个公告id*/
    private String lastAnnounceId;
}
