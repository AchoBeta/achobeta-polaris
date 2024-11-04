package com.achobeta.domain.render.model.bo;

import com.achobeta.domain.render.model.entity.BookEntity;
import com.achobeta.domain.render.model.entity.UserEntity;
import lombok.*;

/**
 * @author chensongmin
 * @description 数据传输胶水类，可无限扩展属性，但不要滥用，
 * 一旦本次业务有的实体是无需用到的，请立刻新做一个 BO 完成符合你需求的业务或删除 BO 多余字段
 * @date 2024/11/4
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RenderBO {

    private UserEntity userEntity;
    private BookEntity bookEntity;

}
