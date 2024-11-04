package com.achobeta.domain.render.model.aggregate;

import com.achobeta.domain.render.model.entity.BookEntity;
import com.achobeta.domain.render.model.entity.ProductEntity;
import com.achobeta.domain.render.model.entity.UserEntity;
import lombok.*;

/**
 * @author chensongmin
 * @description 渲染文本承载类
 * @create 2024/11/3
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RenderAggregate {

    /**
     * 聚合根，可以理解为使用聚合驱动事务一定是有个主体做触发的
     * 这个主体一般来说是用户（用户下单，用户登录，用户点赞）
     */
    private String userId;
    /**
     * 用户实体对象
     */
    private UserEntity userEntity;
    /**
     * 书本实体对象
     */
    private BookEntity bookEntity;
    /**
     * 商品实体对象
     */
    private ProductEntity productEntity;

}
