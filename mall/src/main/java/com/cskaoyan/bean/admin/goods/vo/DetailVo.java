package com.cskaoyan.bean.admin.goods.vo;

import com.cskaoyan.bean.common.MarketGoodsAttribute;
import com.cskaoyan.bean.common.MarketGoodsProduct;
import com.cskaoyan.bean.common.MarketGoodsSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/17 16:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    List<Integer> categoryIds;
    MarketGoodsVo goods;
    List<MarketGoodsAttribute> attributes;
    List<MarketGoodsSpecification> specifications;
    List<MarketGoodsProduct> products;
}
