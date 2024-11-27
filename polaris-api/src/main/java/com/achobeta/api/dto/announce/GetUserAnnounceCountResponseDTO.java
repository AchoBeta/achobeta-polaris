package com.achobeta.api.dto.announce;

import lombok.*;

/**
 * @author huangwenxing
 * @date 2024/11/17
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserAnnounceCountResponseDTO {
    /**该用户拥有的公告数*/
    private Integer count;
}
