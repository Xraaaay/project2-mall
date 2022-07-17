package com.cskaoyan.service.dashboard;

import com.cskaoyan.bean.MarketGoods;
import com.cskaoyan.bean.dashboard.DashBoardVo;
import com.cskaoyan.mapper.MarketGoodsMapper;
import com.cskaoyan.mapper.MarketGoodsProductMapper;
import com.cskaoyan.mapper.MarketOrderMapper;
import com.cskaoyan.mapper.MarketUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 后台系统首页
 *
 * @author Zah
 * @date 2022/07/18 00:01
 */
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    MarketGoodsMapper marketGoodsMapper;
    @Autowired
    MarketUserMapper marketUserMapper;
    @Autowired
    MarketGoodsProductMapper marketGoodsProductMapper;
    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Override
    public DashBoardVo dashBoard() {

        long goodsTotal = marketGoodsMapper.countByExample(null);
        long userTotal = marketUserMapper.countByExample(null);
        long productTotal = marketGoodsProductMapper.countByExample(null);
        long orderTotal = marketOrderMapper.countByExample(null);

        return new DashBoardVo(goodsTotal,userTotal,productTotal,orderTotal);
    }
}
