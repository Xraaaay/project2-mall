package com.cskaoyan.controller.wx.home;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.service.dashboard.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 首页
 *
 * @author fanxing056
 * @date 2022/07/18 16:19
 */

@RestController
@RequestMapping("/wx/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/index")
    public BaseRespVo index() {

        Map<String, Object> commonData = homeService.index();
        return BaseRespVo.ok(commonData);
    }
}
