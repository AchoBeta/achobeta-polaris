package com.achobeta.api;

import com.achobeta.api.dto.announce.GetUserAnnounceRequestDTO;
import com.achobeta.api.dto.announce.GetUserAnnounceResponseDTO;
import com.achobeta.api.dto.announce.ReadAnnounceRequestDTO;
import com.achobeta.types.Response;

import javax.validation.Valid;

/**
 * @author huangwenxing
 * @date 2024/11/11
 */
public interface IAnnounceService {
    Response<GetUserAnnounceResponseDTO> getUserAnnounce(GetUserAnnounceRequestDTO getUserAnnounceRequestDTO);
    Response readAnnounce(@Valid ReadAnnounceRequestDTO readAnnounceRequestDTO);
}
