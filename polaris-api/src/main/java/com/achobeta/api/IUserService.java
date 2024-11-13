package com.achobeta.api;

import com.achobeta.api.dto.user.ModifyUserInfoRequestDTO;
import com.achobeta.api.dto.user.ModifyUserInfoResponseDTO;
import com.achobeta.types.Response;

/**
 * @author yangzhiyao
 * @date 2024/11/6
 */
public interface IUserService {


    /**
     * 修改用户个人信息
     * @param modifyUserInfoRequestDTO
     * @return Response<ModifyUserInfoResponseDTO>
     */
    Response<ModifyUserInfoResponseDTO> modifyUserInfo(ModifyUserInfoRequestDTO modifyUserInfoRequestDTO);

}
