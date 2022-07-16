package com.cskaoyan.controller.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketRole;
import com.cskaoyan.bean.system.MarketRoleOptionsVo;
import com.cskaoyan.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理模块：角色管理
 * @author Xrw
 * @date 2022/7/16 15:10
 */
@RestController
@RequestMapping("admin/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping("list")
    public BaseRespVo list(BasePageInfo info, String name) {
        CommonData<MarketRole> roleList = roleService.list(info, name);
        return BaseRespVo.ok(roleList);
    }

    @RequestMapping("options")
    public BaseRespVo options() {
        CommonData<MarketRoleOptionsVo> optionList = roleService.options();
        return BaseRespVo.ok(optionList);
    }
}
