package com.cskaoyan.service.marketConfig;

import com.cskaoyan.bean.marketConfig.MarketSystemVO;


import java.util.List;
import java.util.Map;

public interface ConfigService {
    List<MarketSystemVO> mall1();

    void updateData(Map<String, String> map);
}

