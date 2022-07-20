package com.cskaoyan.controller.admin.system;

import com.cskaoyan.anno.OperationLog;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.system.*;
import com.cskaoyan.exception.InvalidParamException;
import com.cskaoyan.service.admin.system.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequiresPermissions("admin:admin:list")
    @RequestMapping("list")
    public BaseRespVo list(BasePageInfo info, String name) {
        CommonData<MarketAdminListVo> adminList = adminService.list(info, name);
        return BaseRespVo.ok(adminList);
    }

    @RequiresPermissions("admin:admin:create")
    @OperationLog(action = "添加管理员")
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody MarketAdmin admin) throws Exception {
        checkNameAndPwd(admin.getUsername(), admin.getPassword());
        MarketAdminCreateVo createVo = adminService.create(admin);
        return BaseRespVo.ok(createVo);
    }

    @RequiresPermissions("admin:admin:update")
    @OperationLog(action = "编辑管理员")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody MarketAdmin admin) throws Exception {
        checkNameAndPwd(admin.getUsername(), admin.getPassword());
        MarketAdminUpdateVo updateVo = adminService.update(admin);
        return BaseRespVo.ok(updateVo);
    }

    @RequiresPermissions("admin:admin:delete")
    @OperationLog(action = "删除管理员")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody MarketAdmin admin) {
        adminService.delete(admin);
        return BaseRespVo.ok(null);
    }

    /**
     * 校验用户名和密码的合法性
     * @exception InvalidParamException
     * @author Xrw
     * @date 2022/7/17 16:12
     */
    private void checkNameAndPwd(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new InvalidParamException("用户名和密码不能为空");
        }
        // 用户名校验
        if (!username.matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]{6,12}$")) {
            throw new InvalidParamException("用户名可以包含中文、大小写字母、和数字，长度在6-12之间");
        }
        // 密码校验
        if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}$")) {
            throw new InvalidParamException("密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在6-15之间");
        }
    }
}
