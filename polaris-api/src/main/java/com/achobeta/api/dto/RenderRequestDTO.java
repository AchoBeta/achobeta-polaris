package com.achobeta.api.dto;

import com.achobeta.types.annotation.Creator;
import com.achobeta.types.annotation.FieldDesc;
import com.achobeta.types.annotation.Updater;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenderRequestDTO implements Serializable {

    @NotBlank(message = "修改必须指定用户ID", groups = {Updater.class})
    @Null(message = "新增不能指定用户ID", groups = {Creator.class})
    @FieldDesc(name = "用户ID")
    private String userId;

    @NotBlank
    @FieldDesc(name = "书籍ID")
    private String bookId;

    @NotBlank
    @Email(message = "不是一个合法邮箱地址")
    @FieldDesc(name = "用户收货邮箱")
    private String email;

}
