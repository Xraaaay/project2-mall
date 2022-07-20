package com.cskaoyan.bean.wx.goods.detail;

import com.cskaoyan.bean.common.MarketGoodsSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/20 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationList {
    List<MarketGoodsSpecification> valueList;
    String name;
}
