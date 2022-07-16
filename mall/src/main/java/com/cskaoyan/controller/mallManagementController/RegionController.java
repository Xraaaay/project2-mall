package com.cskaoyan.controller.mallManagementController;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.po.MarketRegionPo;
import com.cskaoyan.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**商场管理的行政区域部分
 * @author changyong
 * @since 2022/07/15 21:28
 */
@RestController
@RequestMapping("admin/region")
public class RegionController {

    @Autowired
    RegionService regionService;
    /**
     * 返回行政区域列表
     * @return com.cskaoyan.bean.BaseRespVo<com.cskaoyan.bean.po.MarketRegionPo>
     * @author changyong
     * @since 2022/07/16 19:47
     */
    @RequestMapping("list")
    public BaseRespVo<MarketRegionPo> list(){
        MarketRegionPo marketRegionPo = regionService.selectByExample();
        return BaseRespVo.ok(marketRegionPo);
    }
}
