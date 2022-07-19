package com.cskaoyan.controller.admin.dashboard;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.dashboard.DashBoardVo;
import com.cskaoyan.service.admin.dashboard.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台系统首页
 *
 * @author Zah
 * @date 2022/07/18 00:13
 */
@RestController
public class AdminDashBoardController {

    @Autowired
    DashBoardService dashBoardService;


    @RequestMapping("admin/dashboard")
    public BaseRespVo dashBoard(){
        DashBoardVo dashBoardVo = dashBoardService.dashBoard();
        return BaseRespVo.ok(dashBoardVo);
    }
}
