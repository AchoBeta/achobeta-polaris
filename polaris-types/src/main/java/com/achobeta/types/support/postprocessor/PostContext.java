package com.achobeta.types.support.postprocessor;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chensongmin
 * @description Post扩展点数据上下文
 * 用于在主流程与分支流程的数据传递
 * @create 2024/11/3
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostContext<T> {

    /**
     * 业务ID，全局唯一
     * <p>新开一个业务模块，就需要在 {@link com.achobeta.types.enums.BizModule} 多开一个枚举映射上</p>
     */
    private String bizId;

    /**
     * 业务名称
     * <p>新开一个业务模块，就需要在 {@link com.achobeta.types.enums.BizModule} 多开一个枚举映射上</p>
     */
    private String bizName;

    /**
     * 业务数据
     */
    private T bizData;

    /**
     * 额外业务数据
     * <p>如果在 processor 处理过程中出现了一些新数据需要承载
     * 而不想修改原本定义好的 BO 对象时，可以往 extra 里放元素
     * </p>
     * <pre>数据格式：
     * Key：变量名，小写开头，可以用常量封装
     * Value：变量值</pre>
     */
    private Map<String, Object> extra;

    public void addExtraData(String key, Object value) {
        if (null == extra) {
            extra = new HashMap<>();
        }
        extra.put(key, value);
    }

    public Object getExtraData(String key) {
        if (null == extra) {
            return null;
        }
        return extra.get(key);
    }

}
