package com.cskaoyan.controller.marketConfig;

import com.cskaoyan.bean.MarketSystem;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.marketConfig.MarketExpreessBO;
import com.cskaoyan.bean.marketConfig.MarketExpreessVO;
import com.cskaoyan.bean.marketConfig.MarketSystemBO;
import com.cskaoyan.bean.marketConfig.MarketSystemVO;
import com.cskaoyan.service.marketConfig.ConfigService;
import com.cskaoyan.util.BeanToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 运费
 * @since 2022/07/17 12:54
 * @author lyx
 */

@RestController
@RequestMapping("admin/config")
public class ExpressConfig {
    @Autowired
    ConfigService configService;

    @GetMapping("express")
    public BaseRespVo express() {
        List<MarketExpreessVO> marketExpressVO = configService.express1();
        Map<String, String> map = marketExpressVO.stream().collect(Collectors.toMap(MarketExpreessVO::getKey_name, MarketExpreessVO::getKey_value));
        return BaseRespVo.ok(map);
    }
    @PostMapping("express")
    public BaseRespVo express(@RequestBody MarketExpreessBO expreessBO) throws IllegalAccessException {
        Map<String, String> map = BeanToMapUtil.beanToMap(expreessBO);
        configService.updateExpressData(map);
        return BaseRespVo.ok(null);
    }
}
