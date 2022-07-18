package com.cskaoyan.bean.goodsVo;

import com.cskaoyan.bean.MarketGoods;
import com.cskaoyan.bean.MarketGoodsAttribute;
import com.cskaoyan.bean.MarketGoodsProduct;
import com.cskaoyan.bean.MarketGoodsSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/18 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBo {
    MarketGoodsVo goods;
    List<MarketGoodsSpecification> specifications;
    List<MarketGoodsProduct> products;
    List<MarketGoodsAttribute> attributes;

}
