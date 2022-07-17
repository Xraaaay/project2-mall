package com.cskaoyan.service.marketConfig;

import com.cskaoyan.bean.MarketSystem;
import com.cskaoyan.bean.marketConfig.MarketExpreessVO;
import com.cskaoyan.bean.marketConfig.MarketOrderVO;
import com.cskaoyan.bean.marketConfig.MarketSystemVO;
import com.cskaoyan.bean.marketConfig.MarketWxVO;
import com.cskaoyan.mapper.MarketSystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就是配置管理模块
 * @since 2022/07/16 09:16
 * @author lyx
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    MarketSystemMapper marketSystemMapper;




    @Override
    public List<MarketSystemVO> mall1() {


        List<MarketSystemVO> marketSystemVOS =  marketSystemMapper.selectLongitudeConfig();

        return marketSystemVOS;
    }

    @Override
    public void updateData(Map<String, String> map) {
        HashMap<String, String> hashMap = new HashMap<>(map);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            marketSystemMapper.UpdateLongitudeConfig(entry.getKey(), entry.getValue());
        }

    }

    @Override
    public List<MarketExpreessVO> express1() {
        List<MarketExpreessVO> marketExpreessVOS =  marketSystemMapper.selectExpressConfig();
        return marketExpreessVOS;

    }

    @Override
    public void updateExpressData(Map<String, String> map) {
        HashMap<String, String> hashMap = new HashMap<>(map);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            marketSystemMapper.UpdateExpressConfig(entry.getKey(), entry.getValue());
        }


    }

    @Override
    public List<MarketOrderVO> order1() {
        List<MarketOrderVO> marketOrderVOS =  marketSystemMapper.selectOrderConfig();
        return marketOrderVOS;
    }

    @Override
    public void updateOrderData(Map<String, String> map) {
        HashMap<String, String> hashMap = new HashMap<>(map);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            marketSystemMapper.UpdateOrderConfig(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public List<MarketWxVO> wx1() {
        List<MarketWxVO> marketWxVOS =  marketSystemMapper.selectWxConfig();
        return marketWxVOS;
    }

    @Override
    public void updateWx(Map<String, String> map) {
        HashMap<String, String> hashMap = new HashMap<>(map);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {

                marketSystemMapper.UpdateWxConfig1(entry.getKey(), entry.getValue());
        }
    }
}


