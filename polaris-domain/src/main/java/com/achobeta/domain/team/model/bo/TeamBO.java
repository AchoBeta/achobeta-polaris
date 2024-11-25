package com.achobeta.domain.team.model.bo;

import com.achobeta.domain.team.model.entity.PositionEntity;
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

    private PositionEntity positionEntity;
    private List<PositionEntity> positionEntityList;

}
