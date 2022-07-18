package com.cskaoyan.bean.goodsVo;

import com.cskaoyan.bean.MarketGoodsAttribute;
import com.cskaoyan.bean.MarketGoodsProduct;
import com.cskaoyan.bean.MarketGoodsSpecification;
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
