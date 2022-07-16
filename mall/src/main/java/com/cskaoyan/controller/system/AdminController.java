package com.cskaoyan.controller.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketAdmin;
import com.cskaoyan.bean.system.MarketAdminListVo;
import com.cskaoyan.service.system.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理模块：管理员管理
 * @author Xrw
 * @date 2022/7/16 13:59
 */
@RestController
@RequestMapping("admin/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("list")
    public BaseRespVo list(BasePageInfo info, String name) {
        CommonData<MarketAdminListVo> adminList = adminService.list(info, name);
        return BaseRespVo.ok(adminList);
    }
}
