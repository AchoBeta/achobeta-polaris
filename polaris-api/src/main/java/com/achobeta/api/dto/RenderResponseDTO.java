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
public class RenderResponseDTO {

    private String userName;
    private String bookName;
    private String bookContent;

}
