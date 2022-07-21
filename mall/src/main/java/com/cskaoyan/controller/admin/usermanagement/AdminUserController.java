package com.cskaoyan.controller.admin.usermanagement;

import com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.exception.InvalidParamException;
import com.cskaoyan.service.admin.usermanagement.UserService;
import jodd.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class AdminUserController {

    @Autowired
    UserService userService;

    @RequiresPermissions("admin:user:list")
    @RequestMapping("list")
    public BaseRespVo list(BaseParam page,String username,String mobile){

        UserListVo userList = userService.getUserList(page, username, mobile);

        return BaseRespVo.ok(userList);
    }

    @RequiresPermissions("admin:user:detail")
    @RequestMapping("detail")
    public BaseRespVo detail(Integer id){

        MarketUser userDetail = userService.getUserDetail(id);

        return BaseRespVo.ok(userDetail);
    }

    /**
     * 用户信息更新
     * @param marketUser
     * @return com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/20 11:37
     */
    @RequiresPermissions("admin:user:update")
    @PostMapping("update")
    public BaseRespSuccessVo userUpdate(@RequestBody MarketUser marketUser) {
        //正则判断
        if (StringUtil.isEmpty(marketUser.getNickname())||StringUtil.isEmpty(marketUser.getPassword())) {
            throw new InvalidParamException("用户名和密码不能为空！");
        }
        // 用户名校验
        if (!marketUser.getNickname().matches("^[\\u4E00-\\u9FA5A-Za-z0-9]{6,12}$")) {
            throw new InvalidParamException("用户名可以包含中文、大小写字母、和数字，长度在6-12之间");
        }
        // 密码校验
        if (marketUser.getPassword().equals("********")) {
            //密码未修改
            marketUser.setPassword(null);
        }else {
            if (!marketUser.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}$")) {
                throw new InvalidParamException("密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在6-15之间");
            }
        }
        //手机号码校验
        if (!marketUser.getMobile().matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$")) {
            throw new InvalidParamException("请输入正确的手机号");
        }
        Integer updateNum=userService.updateUser(marketUser);
        return BaseRespSuccessVo.success(updateNum);
    }

}
