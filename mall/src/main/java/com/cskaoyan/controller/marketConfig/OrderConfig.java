package com.cskaoyan.controller.marketConfig;

import com.cskaoyan.bean.MarketOrder;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.marketConfig.MarketExpreessBO;
import com.cskaoyan.bean.marketConfig.MarketExpreessVO;
import com.cskaoyan.bean.marketConfig.MarketOrderBO;
import com.cskaoyan.bean.marketConfig.MarketOrderVO;
import com.cskaoyan.service.marketConfig.ConfigService;
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
public class OrderConfig {
    @Autowired
    ConfigService configService;

    @GetMapping("order")
    public BaseRespVo order() {
        List<MarketOrderVO> marketOrderVO = configService.order1();
        Map<String, String> map = marketOrderVO.stream().collect(Collectors.toMap(MarketOrderVO::getKey_name, MarketOrderVO::getKey_value));
        return BaseRespVo.ok(map);

    }

    @PostMapping("order")
    public BaseRespVo express(@RequestBody MarketOrderBO orderBO) throws IllegalAccessException {
        Map<String, String> map = BeanToMapUtil.beanToMap(orderBO);
        configService.updateOrderData(map);
        return BaseRespVo.ok(null);
    }
}
