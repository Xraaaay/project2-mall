package com.cskaoyan.controller.admin.mallmanagement;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketRegionPo;
import com.cskaoyan.service.admin.mallmanagement.RegionService;
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
     * @return com.cskaoyan.bean.BaseRespVo<com.cskaoyan.bean.admin.mallmanagement.po.MarketRegionPo>
     * @author changyong
     * @since 2022/07/16 19:47
     */
    @RequestMapping("list")
    public BaseRespVo<MarketRegionPo> list(){
        MarketRegionPo marketRegionPo = regionService.selectByExample();
        return BaseRespVo.ok(marketRegionPo);
    }
}
