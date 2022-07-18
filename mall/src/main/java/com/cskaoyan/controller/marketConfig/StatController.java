package com.cskaoyan.controller.marketConfig;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.marketConfig.StatGoodsVO;
import com.cskaoyan.bean.marketConfig.StatOrderVO;
import com.cskaoyan.bean.marketConfig.StatUserVO;
import com.cskaoyan.service.marketConfig.ConfigService;
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


    @RequestMapping("user")
    public BaseRespVo user() {
       StatUserVO statUserVO = configService.statUser();

        return BaseRespVo.ok(statUserVO);
    }

    @RequestMapping("goods")
    public BaseRespVo goods() {
        StatGoodsVO statGserVO = configService.statGoods();
        return BaseRespVo.ok(statGserVO);
    }


    @RequestMapping("order")
    public BaseRespVo order() {

        StatOrderVO statOrderVO = configService.statOrder();

        return BaseRespVo.ok(statOrderVO);

    }


}
