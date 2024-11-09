package com.achobeta.api.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class RenderRequestDTO implements Serializable {

    private String userId;
    private String bookId;

}
