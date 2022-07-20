package com.cskaoyan.controller.admin.marketconfig;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.marketconfig.StatGoodsVO;
import com.cskaoyan.bean.admin.marketconfig.StatOrderVO;
import com.cskaoyan.bean.admin.marketconfig.StatUserVO;
import com.cskaoyan.service.admin.marketconfig.ConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计报表
 * @since 2022/07/18 09:27
 * @author lyx
 */

@RestController
@RequestMapping("admin/stat")
public class StatController {
    @Autowired
    ConfigService configService;

    @RequiresPermissions("admin:stat:user")
    @RequestMapping("user")
    public BaseRespVo user() {
       StatUserVO statUserVO = configService.statUser();

        return BaseRespVo.ok(statUserVO);
    }

    @RequiresPermissions("admin:stat:goods")
    @RequestMapping("goods")
    public BaseRespVo goods() {
        StatGoodsVO statGserVO = configService.statGoods();
        return BaseRespVo.ok(statGserVO);
    }

    @RequiresPermissions("admin:stat:order")
    @RequestMapping("order")
    public BaseRespVo order() {

        StatOrderVO statOrderVO = configService.statOrder();

        return BaseRespVo.ok(statOrderVO);

    }


}
