package com.achobeta.test.composite;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.types.annotation.FieldDesc;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.id.SnowflakeIdWorker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TeamComposite extends AbstractCompositeNode {

    @FieldDesc(name = "组合树业务ID, 雪花算法生成, 持久化到 Redis")
    private String id;
    private String pid;
    private String nodeName;
    private String teamId;
    private TeamStructureType nodeType;
//    private AbstractCompositeNode rootNode;
    private List<AbstractCompositeNode> childNode = new ArrayList<>();

    @Override
    protected void addCompositeNode(AbstractCompositeNode node) {
        this.childNode.add(node);
    }

    @Override
    public void delCompositeNode(AbstractCompositeNode node) {
        delCompositeNode(node, AbstractCompositeNode.DEL_NODE_ONLY);
    }

    protected void delCompositeNode(AbstractCompositeNode node, String delModel) {
        if (AbstractCompositeNode.DEL_NODE_AND_CHILD.equals(delModel)) {
            // 注意 list 的删除要用迭代器
            TeamComposite removeNode = (TeamComposite) node;
            Iterator<AbstractCompositeNode> iterator = this.childNode.iterator();
            while (iterator.hasNext()) {
                TeamComposite composite = (TeamComposite) iterator.next();
                if (Objects.equals(composite.getPid(), removeNode.getPid())) {
                    iterator.remove();
                    break;
                }
            }
        } else if (AbstractCompositeNode.DEL_NODE_ONLY.equals(delModel)) {

        }
    }

    @Override
    public AbstractCompositeNode generateCompositeTree(@Valid List<PositionEntity> positionEntityList) {
        List<TeamComposite> teamCompositeList = positionEntityList.stream()
            .map(positionEntity -> TeamComposite.builder()
//                .id(positionEntity.getId())
                .id(SnowflakeIdWorker.nextIdStr())
                .pid(positionEntity.getPid())
                .teamId(positionEntity.getTeamId())
                .nodeName(positionEntity.getNodeName())
                .nodeType(TeamStructureType.getType(positionEntity.getNodeType()))
                .build())
            .collect(Collectors.toList());

        Map<String, List<TeamComposite>> groupList = teamCompositeList.stream()
            .collect(Collectors.groupingBy(TeamComposite::getPid));

        teamCompositeList.forEach(teamComposite -> {
            List<TeamComposite> compositeList = groupList.get(teamComposite.getId());
            teamComposite.setChildNode(CollectionUtil.isEmpty(compositeList)
                ? new ArrayList<>() :
                compositeList.stream()
                    .map(x -> (AbstractCompositeNode) x)
                    .collect(Collectors.toList()));
        });
        // 返回根节点
        return teamCompositeList.stream()
            .filter(x -> x.getPid().equals(x.getTeamId()))
            .findFirst()
            .orElseThrow(() -> new AppException(
                GlobalServiceStatusCode.TEAM_GENERATE_TREE_FAIL.getCode(),
                GlobalServiceStatusCode.TEAM_GENERATE_TREE_FAIL.getMessage()));
    }

    @Getter
    @AllArgsConstructor
    enum TeamStructureType {

        MEMBER(1, "团队成员"),
        POSITION(2, "团队职位"),

        ;

        private final Integer code;
        private final String info;

        public static TeamStructureType getType(Integer code) {
            for (TeamStructureType type : TeamStructureType.values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
    }

}
