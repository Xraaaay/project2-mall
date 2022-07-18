package com.cskaoyan.controller.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketRole;
import com.cskaoyan.bean.system.MarketRoleCreateVo;
import com.cskaoyan.bean.system.MarketRoleOptionsVo;
import com.cskaoyan.bean.system.SystemPermissions;
import com.cskaoyan.exception.system.InvalidParamException;
import com.cskaoyan.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @RequestMapping("create")
    public BaseRespVo create(@RequestBody MarketRole role) {
        checkRoleName(role.getName());
        MarketRoleCreateVo createVo = roleService.create(role);
        return BaseRespVo.ok(createVo);
    }

    @RequestMapping("update")
    public BaseRespVo update(@RequestBody MarketRole role) {
        checkRoleName(role.getName());
        roleService.update(role);
        return BaseRespVo.ok(null);
    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody MarketRole role) {
        roleService.delete(role);
        return BaseRespVo.ok(null);
    }

    @GetMapping("permissions")
    public BaseRespVo getPermissions(Integer roleId) {
        Map<String, Object> map = roleService.getPermissions(roleId);
        return BaseRespVo.ok(map);
    }

    @PostMapping("permissions")
    public BaseRespVo setPermissions(@RequestBody Map map) {
        Integer roleId = (Integer) map.get("roleId");
        List<String > permissions = (List<String>) map.get("permissions");
        roleService.setPermissions(roleId, permissions);
        return BaseRespVo.ok(null);
    }

    /**
     * 自定义请求，根据json请求生成market_role_permission表数据
     * @author Xrw
     * @date 2022/7/17 23:05
     */
    @RequestMapping("getpermissions")
    public BaseRespVo permissions(@RequestBody SystemPermissions systemPermissions) {
        roleService.permissions(systemPermissions);
        return BaseRespVo.ok(null);
    }

    private void checkRoleName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new InvalidParamException("角色名不能为空");
        }
        // xrw 用户名校验
        if (name.length() < 5 || name.length() > 10) {
            throw new InvalidParamException("角色名不合法");
        }
    }
}
