package com.cskaoyan.controller.admin.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.system.MarketLog;
import com.cskaoyan.service.admin.system.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理模块：操作日志
 * @author Xrw
 * @date 2022/7/16 16:13
 */
@RestController
@RequestMapping("admin/log")
public class LogController {

    @Autowired
    LogService logService;

    @RequiresPermissions("admin:log:list")
    @RequestMapping("list")
    public BaseRespVo list(BasePageInfo info, String name) {
        CommonData<MarketLog> logList = logService.list(info, name);
        return BaseRespVo.ok(logList);
    }
}
