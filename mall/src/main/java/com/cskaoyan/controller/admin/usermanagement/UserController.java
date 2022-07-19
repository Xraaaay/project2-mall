package com.cskaoyan.controller.admin.usermanagement;

import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.service.admin.usermanagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理之会员管理
 *
 * @author Zah
 * @date 2022/07/18 17:36
 */
@RestController
@RequestMapping("admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseRespVo list(BaseParam page,String username,String mobile){

        UserListVo userList = userService.getUserList(page, username, mobile);

        return BaseRespVo.ok(userList);
    }


    @RequestMapping("detail")
    public BaseRespVo detail(Integer id){

        MarketUser userDetail = userService.getUserDetail(id);

        return BaseRespVo.ok(userDetail);
    }
}
