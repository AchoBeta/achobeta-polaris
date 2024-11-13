package com.achobeta.api;

import com.achobeta.api.dto.user.QueryUserInfoRequestDTO;
import com.achobeta.api.dto.user.QueryUserInfoResponseDTO;
import com.achobeta.types.Response;

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
    Response<QueryUserInfoResponseDTO> queryUserCenterInfo(QueryUserInfoRequestDTO queryUserInfoRequestDTO);


}
