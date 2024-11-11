package com.achobeta.api.dto;

import java.io.Serializable;
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
public class RenderResponseDTO implements Serializable {

    private String userName;
    private String bookName;
    private String bookContent;

}
