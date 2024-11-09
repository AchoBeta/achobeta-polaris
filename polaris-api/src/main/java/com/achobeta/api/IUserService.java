package com.achobeta.api;

import com.achobeta.api.dto.user.ModifyUserInfoRequestDTO;
import com.achobeta.api.dto.user.ModifyUserInfoResponseDTO;
import com.achobeta.api.dto.user.UserInfoRequestDTO;
import com.achobeta.api.dto.user.UserInfoResponseDTO;
import com.achobeta.api.response.Response;

/**
 * @author yangzhiyao
 * @date 2024/11/6
 */
public interface IUserService {

    Response<UserInfoResponseDTO> center(UserInfoRequestDTO userInfoRequestDTO);

    Response<ModifyUserInfoResponseDTO> modify(ModifyUserInfoRequestDTO modifyUserInfoRequestDTO);

}
