package com.cskaoyan.service.admin.dashboard;

import com.cskaoyan.bean.admin.dashboard.DashBoardVo;
import com.cskaoyan.mapper.common.MarketGoodsMapper;
import com.cskaoyan.mapper.common.MarketGoodsProductMapper;
import com.cskaoyan.mapper.common.MarketOrderMapper;
import com.cskaoyan.mapper.common.MarketUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台系统首页
 *
 * @author Zah
 * @date 2022/07/18 00:01
 */
@Service
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
