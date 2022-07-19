package com.cskaoyan.service.admin.marketconfig;

import com.cskaoyan.bean.admin.marketconfig.*;


import java.util.List;
import java.util.Map;

public interface ConfigService {
    List<MarketSystemVO> mall1();

    void updateData(Map<String, String> map);

    List<MarketExpreessVO> express1();

    void updateExpressData(Map<String, String> map);

    List<MarketOrderVO> order1();

    void updateOrderData(Map<String, String> map);

    List<MarketWxVO> wx1();

    void updateWx(Map<String, String> map);

    StatUserVO statUser();

    StatOrderVO statOrder();

    StatGoodsVO statGoods();
}

