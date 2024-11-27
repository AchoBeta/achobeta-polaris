package com.achobeta.api.dto.announce;

import lombok.*;

import java.util.List;
/**
 * @author huangwenxing
 * @date 2024/11/11
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserAnnounceResponseDTO {
    /**用户公告集合*/
    private List userAnnounce;
    /**是否还有更多数据*/
    private boolean more;
}
