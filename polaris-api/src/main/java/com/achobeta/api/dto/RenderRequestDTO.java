package com.achobeta.api.dto;

import lombok.*;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenderRequestDTO {

    private String userId;
    private String bookId;

}
