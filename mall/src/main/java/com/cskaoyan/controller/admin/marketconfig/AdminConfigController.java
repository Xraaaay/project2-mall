package com.cskaoyan.controller.admin.marketconfig;


import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.marketconfig.MarketSystemBO;
import com.cskaoyan.bean.admin.marketconfig.MarketSystemVO;

import com.cskaoyan.service.admin.marketconfig.ConfigService;
import com.cskaoyan.util.BeanToMapUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 配置管理的控制模块
 * @since 2022/07/16 09:20
 * @author lyx
 */
@RestController
@RequestMapping("admin/config")
public class AdminConfigController {
    @Autowired
    ConfigService configService;

    @RequiresPermissions("admin:config:mall:list")
   @GetMapping("mall")
    public BaseRespVo mall1() {
       List<MarketSystemVO> marketSystemVO = configService.mall1();
       Map<String, String> map = marketSystemVO.stream().collect(Collectors.toMap(MarketSystemVO::getKey_name, MarketSystemVO::getKey_value));
       return BaseRespVo.ok(map);
    }

    @RequiresPermissions("admin:config:mall:updateConfigs")
    @PostMapping("mall")
    public BaseRespVo mall2(@RequestBody MarketSystemBO systemBO) throws IllegalAccessException {
        Map<String, String> map = BeanToMapUtil.beanToMap(systemBO);
        configService.updateData(map);
        return BaseRespVo.ok(null);
    }


}
