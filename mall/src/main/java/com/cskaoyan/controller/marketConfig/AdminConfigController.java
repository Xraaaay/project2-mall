package com.cskaoyan.controller.marketConfig;


import com.cskaoyan.bean.common.BaseRespVo;
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
 * 配置管理的控制模块
 * @since 2022/07/16 09:20
 * @author lyx
 */
@RestController
@RequestMapping("/admin/config")
public class AdminConfigController {
    @Autowired
    ConfigService configService;

   @GetMapping("mall")
    public BaseRespVo mall1() {
       List<MarketSystemVO> marketSystemVO = configService.mall1();
       Map<String, String> map = marketSystemVO.stream().collect(Collectors.toMap(MarketSystemVO::getKey_name, MarketSystemVO::getKey_value));
       return BaseRespVo.ok(map);
    }

    @PostMapping("mall")
    public BaseRespVo mall2(@RequestBody MarketSystemBO systemBO) throws IllegalAccessException {
        Map<String, String> map = BeanToMapUtil.beanToMap(systemBO);
        configService.updateData(map);
        return BaseRespVo.ok(null);


    }


}
