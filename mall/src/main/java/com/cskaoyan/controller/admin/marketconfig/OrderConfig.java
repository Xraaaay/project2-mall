package com.cskaoyan.controller.admin.marketconfig;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.marketconfig.MarketOrderBO;
import com.cskaoyan.bean.admin.marketconfig.MarketOrderVO;
import com.cskaoyan.service.admin.marketconfig.ConfigService;
import com.cskaoyan.util.BeanToMapUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * @since 2022/07/17 21:00
 * @author lyx
 */

@RestController
@RequestMapping("admin/config")
public class OrderConfig {
    @Autowired
    ConfigService configService;

    @RequiresPermissions("admin:config:order:list")
    @GetMapping("order")
    public BaseRespVo order() {
        List<MarketOrderVO> marketOrderVO = configService.order1();
        Map<String, String> map = marketOrderVO.stream().collect(Collectors.toMap(MarketOrderVO::getKey_name, MarketOrderVO::getKey_value));
        return BaseRespVo.ok(map);

    }

    @RequiresPermissions("admin:config:order:updateConfigs")
    @PostMapping("order")
    public BaseRespVo express(@RequestBody MarketOrderBO orderBO) throws IllegalAccessException {
        Map<String, String> map = BeanToMapUtil.beanToMap(orderBO);
        configService.updateOrderData(map);
        return BaseRespVo.ok(null);
    }
}
