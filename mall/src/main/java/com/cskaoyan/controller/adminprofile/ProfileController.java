package com.cskaoyan.controller.adminprofile;

import com.cskaoyan.bean.common.BaseRespVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统提示？至少不会过一段时间就不能使用登出功能了
 *
 * @author Zah
 * @date 2022/07/18 17:19
 */
@RestController
@RequestMapping("admin/profile")
public class ProfileController {

    @RequestMapping("nnotice")
    public BaseRespVo nnotice(){
        return BaseRespVo.ok(0);
    }
}
