package com.cskaoyan.service.wx.footprint;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.BaseParam;

public interface FootprintService {

    /**
     * 用户浏览足迹列表
     * @param page
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 11:01
     */
    UserListVo getFootprintList(BaseParam page);

    /**
     * 逻辑删除用户浏览足迹单个足迹信息
     * @param id
     * @return void
     * @author Zah
     * @date 2022/07/20 11:01
     */
    void postFootprintDelete(Integer id);
}
