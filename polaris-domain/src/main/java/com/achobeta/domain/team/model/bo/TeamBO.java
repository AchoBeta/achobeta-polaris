package com.achobeta.domain.team.model.bo;

import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.user.model.entity.UserEntity;
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

    private String userId;
    private String teamId;
    private UserEntity userEntity;
    private PositionEntity positionEntity;
    private List<PositionEntity> positionEntityList;
}
