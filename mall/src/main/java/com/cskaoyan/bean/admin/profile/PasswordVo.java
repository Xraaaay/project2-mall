package com.cskaoyan.bean.admin.profile;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String newPassword;
    private String newPassword2;

}
