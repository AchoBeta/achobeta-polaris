package com.achobeta.api.dto;

import com.achobeta.types.common.BaseRequestParam;
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
public class RenderRequestDTO extends BaseRequestParam {

    private String userId;
    private String bookId;

}
