package com.cskaoyan.controller.dashboard;

import com.cskaoyan.bean.common.BaseRespVo;
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
public class DashBoardController {

    @Autowired


    @RequestMapping("admin/dashboard")
    public BaseRespVo dashBoard(){
        return null;
    }
}
