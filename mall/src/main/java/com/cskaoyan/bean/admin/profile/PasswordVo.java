package com.cskaoyan.bean.admin.profile;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * 修改后台系统管理员密码
 *
 * @author Zah
 * @date 2022/07/21 14:32
 */
@NoArgsConstructor
@Data
public class PasswordVo {

    private String oldPassword;

    @Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}$",
            message="密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在6-15之间")
    private String newPassword;

    private String newPassword2;

}
