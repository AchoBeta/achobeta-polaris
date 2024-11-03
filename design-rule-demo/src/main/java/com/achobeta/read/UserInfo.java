package com.achobeta.read;

import lombok.*;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserInfo {

    private String name;
    private boolean isAuth;

}
