package com.cskaoyan.bean.admin.goods.bo;

import com.cskaoyan.bean.common.MarketGoodsAttribute;
import com.cskaoyan.bean.common.MarketGoodsProduct;
import com.cskaoyan.bean.common.MarketGoodsSpecification;
import com.cskaoyan.bean.admin.goods.vo.MarketGoodsVo;
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
