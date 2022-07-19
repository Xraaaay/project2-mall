package com.cskaoyan.service.admin.marketconfig;

import com.cskaoyan.bean.admin.marketconfig.*;
import com.cskaoyan.mapper.common.MarketSystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public StatUserVO statUser() {
          List<String> columns = new ArrayList<>();
          columns.add("day");
          columns.add( "users");
        List<StatRowsVO> rows = marketSystemMapper.selectStatUser();
        StatUserVO statUserVO = new StatUserVO();
        statUserVO.setColumns(columns);
        statUserVO.setRows(rows);
        return statUserVO;
    }

    @Override
    public StatOrderVO statOrder() {
        List<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add( "orders");
        columns.add( "customers");
        columns.add( "amount");
        columns.add( "pcr");
        List<StatOrderRowsVO> rows = marketSystemMapper.selectStatOrder();
        StatOrderVO statOrderVO = new StatOrderVO();
        statOrderVO.setColumns(columns);
        statOrderVO.setRows(rows);
        return statOrderVO;
    }

    @Override
    public StatGoodsVO statGoods() {
        List<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add( "orders");
        columns.add( "products");
        columns.add( "amount");
        List<StatGoodsRowsVO> rows = marketSystemMapper.selectStatGoods();
        StatGoodsVO statGoodsVO = new StatGoodsVO();
        statGoodsVO.setColumns(columns);
        statGoodsVO.setRows(rows);
        return statGoodsVO;
    }
}


