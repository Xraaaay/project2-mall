package com.cskaoyan.service.admin.mallmanagement;

import com.cskaoyan.bean.admin.mallmanagement.po.MarketRegionPo;


public interface RegionService {
    /**
     * 返回行政区域列表
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarketRegionPo
     * @author changyong
     * @since 2022/07/16 18:18
     */
    MarketRegionPo selectByExample();
}
