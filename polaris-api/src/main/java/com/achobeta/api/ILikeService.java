package com.achobeta.api;

import com.achobeta.api.dto.like.LikeRequestDTO;
import com.achobeta.types.Response;

import javax.validation.Valid;

/**
 * @author huangwenxing
 * @date 2024/11/17
 */
public interface ILikeService {
    Response like(@Valid LikeRequestDTO likeRequestDTO);
}
