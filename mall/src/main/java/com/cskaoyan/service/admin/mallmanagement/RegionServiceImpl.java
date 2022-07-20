package com.cskaoyan.service.admin.mallmanagement;

import com.cskaoyan.bean.common.MarketRegion;
import com.cskaoyan.bean.common.MarketRegionExample;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketRegionPo;
import com.cskaoyan.mapper.common.MarketRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 15:10
 */
@Component
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    MarketRegionMapper marketRegionMapper;

    /**
     * 返回行政区域列表
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarketRegionPo
     * @author changyong
     * @since 2022/07/16 18:18
     */
    @Override
    public MarketRegionPo selectByExample() {
        //获取list
        MarketRegionExample marketRegionExample1 = new MarketRegionExample();
        MarketRegionExample.Criteria criteria1 = marketRegionExample1.createCriteria();
        criteria1.andPidEqualTo(0)
                .andTypeEqualTo((byte) 1);
        List<MarketRegion> marketRegions1 = marketRegionMapper.selectByExample(marketRegionExample1);
        //以下是两次foreach循环，给children赋值
        for (MarketRegion marketRegion : marketRegions1) {
            MarketRegionExample marketRegionExample2 = new MarketRegionExample();
            MarketRegionExample.Criteria criteria2 = marketRegionExample2.createCriteria();
            criteria2.andPidEqualTo(marketRegion.getId())
                    .andTypeEqualTo((byte) 2);
            List<MarketRegion> marketRegions2 = marketRegionMapper.selectByExample(marketRegionExample2);
            for (MarketRegion region : marketRegions2) {
                MarketRegionExample marketRegionExample3 = new MarketRegionExample();
                MarketRegionExample.Criteria criteria3 = marketRegionExample3.createCriteria();
                criteria3.andPidEqualTo(region.getId())
                        .andTypeEqualTo((byte) 3);
                List<MarketRegion> marketRegions3 = marketRegionMapper.selectByExample(marketRegionExample3);
                region.setChildren(marketRegions3);
            }
            marketRegion.setChildren(marketRegions2);
            //TODO 我修改了bean中MarketRegion类，增加了children成员变量，是否可以？是否应该？
            //TODO 参考网页中 返回children对象没有pid，第二层children没有children成员变量
        }
        //获取count
        MarketRegionExample marketRegionExample4 = new MarketRegionExample();
        MarketRegionExample.Criteria criteria4 = marketRegionExample4.createCriteria();
        criteria4.andPidEqualTo(0)
                .andTypeEqualTo((byte) 1);
        long count = marketRegionMapper.countByExample(marketRegionExample4);
        return new MarketRegionPo(count, 1, count, 1, marketRegions1);
    }
}
