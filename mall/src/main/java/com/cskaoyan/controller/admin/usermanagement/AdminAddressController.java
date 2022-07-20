package com.cskaoyan.controller.admin.usermanagement;

import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.service.admin.usermanagement.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理之收货地址
 *
 * @author Zah
 * @date 2022/07/18 18:13
 */
@RestController
@RequestMapping("admin/address")
public class AdminAddressController {

    @Autowired
    UserService userService;

    @RequiresPermissions("admin:address:list")
    @RequestMapping("list")
    public BaseRespVo list(BaseParam page,String name,Integer userId){

        UserListVo addressList = userService.getAddressList(page, name, userId);

        return BaseRespVo.ok(addressList);
    }
}
