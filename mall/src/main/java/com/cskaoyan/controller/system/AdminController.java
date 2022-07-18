package com.cskaoyan.controller.system;

import com.cskaoyan.anno.SecurityOperationLog;
import com.cskaoyan.anno.SecurityOperationType;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.*;
import com.cskaoyan.exception.common.InvalidParamException;
import com.cskaoyan.service.system.AdminService;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping("list")
    public BaseRespVo list(BasePageInfo info, String name) {
        CommonData<MarketAdminListVo> adminList = adminService.list(info, name);
        return BaseRespVo.ok(adminList);
    }

    @SecurityOperationLog(SecurityOperationType.CREAT)
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody MarketAdmin admin) throws Exception {
        checkNameAndPwd(admin.getUsername(), admin.getPassword());
        MarketAdminCreateVo createVo = adminService.create(admin);
        return BaseRespVo.ok(createVo);
    }

    @SecurityOperationLog(SecurityOperationType.UPDATE)
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody MarketAdmin admin) {
        checkNameAndPwd(admin.getUsername(), admin.getPassword());
        MarketAdminUpdateVo updateVo = adminService.update(admin);
        return BaseRespVo.ok(updateVo);
    }

    @SecurityOperationLog(SecurityOperationType.DELETE)
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
        // xrw 用户名、密码校验
        if (username.length() < 6 || username.length() > 15) {
            throw new InvalidParamException("用户名不合法");
        }
    }
}
