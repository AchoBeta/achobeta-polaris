package com.achobeta.types.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chensongmin
 * @description
 * 验证器必须实现ConstraintValidator接口，该接口是泛型接口
 * 第一个参数是该验证器要用于在那个注解，第二个参数是该注解应用在什么数据类型上
 * @date 2024/11/11
 */
@Slf4j
public class PrefixConstraintValidator implements ConstraintValidator<PrefixConstraint, String> {

    private String prefix;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return false ;
        }
        log.info("前缀约束 {}", prefix);
        log.info("参数传递 {}", value);
        return value.startsWith(prefix);
    }
    @Override
    public void initialize(PrefixConstraint pc) {
        prefix = pc.value() ;
    }
}
