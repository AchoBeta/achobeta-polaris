package com.achobeta.api;

import com.achobeta.api.dto.announce.*;
import com.achobeta.types.Response;

import javax.validation.Valid;

/**
 * @author huangwenxing
 * @date 2024/11/11
 */
public interface IAnnounceService {
    Response<GetUserAnnounceResponseDTO> getUserAnnounce(@Valid GetUserAnnounceRequestDTO getUserAnnounceRequestDTO);
    Response readAnnounce(@Valid ReadAnnounceRequestDTO readAnnounceRequestDTO);
    Response<GetUserAnnounceCountResponseDTO> getUserAnnounceCount(@Valid GetUserAnnounceCountRequestDTO getUserAnnounceCountRequestDTO);
    Response readAllAnnounce(@Valid ReadAllAnnounceRequestDTO readAllAnnounceRequestDTO);
}
