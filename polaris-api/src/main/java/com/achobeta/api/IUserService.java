package com.achobeta.api;

import com.achobeta.api.dto.user.QueryUserInfoRequestDTO;
import com.achobeta.api.dto.user.QueryUserInfoResponseDTO;
import com.achobeta.types.Response;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author yangzhiyao
 * @date 2024/11/6
 */
public interface IUserService {

    /**
     * 查询用户中心信息
     * @param queryUserInfoRequestDTO
     * @return Response<QueryUserInfoResponseDTO>
     */
    Response<QueryUserInfoResponseDTO> queryUserCenterInfo(@Valid QueryUserInfoRequestDTO queryUserInfoRequestDTO);


}
