package com.cskaoyan.service.wx.user;

import com.cskaoyan.bean.admin.mallmanagement.po.WxUserIndexOrderPo;

public interface WxUserService {
    /**
     * 返回个人中心首页
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.WxUserIndexOrderPo
     * @author changyong
     * @since 2022/07/20 11:53
     */
    WxUserIndexOrderPo index();
}
