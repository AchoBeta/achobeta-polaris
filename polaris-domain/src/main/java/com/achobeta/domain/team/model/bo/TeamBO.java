package com.achobeta.domain.team.model.bo;

import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.annotation.FieldDesc;

import lombok.*;

import java.util.List;

/**
 * @author yangzhiyao
 * @description
 * @date 2024/11/7
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TeamBO {

    @FieldDesc(name = "团队ID")
    private String teamId;

    @FieldDesc(name = "操作人的用户ID")
    private String userId;
    private UserEntity userEntity;
    private PositionEntity positionEntity;
    private List<UserEntity> members;
    private List<PositionEntity> positionEntityList;


}
