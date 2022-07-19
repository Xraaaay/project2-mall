package com.cskaoyan.controller.admin.marketconfig;


import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.marketconfig.MarketWxBO;
import com.cskaoyan.bean.admin.marketconfig.MarketWxVO;
import com.cskaoyan.service.admin.marketconfig.ConfigService;
import com.cskaoyan.util.BeanToMapUtil;
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
public class WxConfig {
    @Autowired
    ConfigService configService;

    @GetMapping("wx")
    public BaseRespVo wx() {
        // List<MarketWxVO> marketWxVOS = configService.wx1();
        List<MarketWxVO> marketWxVOS = configService.wx1();
        Map<String, String> map = marketWxVOS.stream().collect(Collectors.toMap(MarketWxVO::getKey_name, MarketWxVO::getKey_value));
        return BaseRespVo.ok(map);

    }

    @PostMapping("wx")
    public BaseRespVo express(@RequestBody MarketWxBO marketWxBO) throws IllegalAccessException {
        Map<String, String> map = BeanToMapUtil.beanToMap(marketWxBO);
        configService.updateWx(map);
        return BaseRespVo.ok(null);
    }
}
