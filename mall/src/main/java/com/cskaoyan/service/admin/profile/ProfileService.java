package com.cskaoyan.service.admin.profile;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.BaseParam;

public interface ProfileService {

    /**
     * 通知信息
     * @param page
     * @param title
     * @param type
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 20:09
     */
    UserListVo isNotice(BaseParam page, String title, String type);
}
