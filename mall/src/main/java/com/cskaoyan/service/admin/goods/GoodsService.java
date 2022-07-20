package com.cskaoyan.service.admin.goods;

import com.cskaoyan.bean.admin.goods.po.MarketGoodsPo;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.goods.vo.CatAndBrandVo;
import com.cskaoyan.bean.admin.goods.bo.CreateBo;
import com.cskaoyan.bean.admin.goods.vo.DetailVo;
import com.cskaoyan.bean.admin.goods.bo.UpdateBo;
import com.cskaoyan.bean.common.MarketGoods;

/**
 * @author pqk
 * @since 2022/07/16 21:14
 */

public interface GoodsService {

    /**
     * @description 展示商品列表
     * @return  二级VO
     * @param page 页码
     * @param limit 每页显示数量
     * @param sort 按照创建时间 add_time
     * @param order 降序排序
     * @author pqk
     * @date 2022/07/16 22:21
     */
    CommonData list(Integer page, Integer limit, String sort, String order);

    /**
     * @description 删除指定商品
     * @return  null
     * @param id
     * @author pqk
     * @date 2022/07/16 22:58
     */
    void delete(Integer id);

    /**
     * @description 商品回显
     * @return
     * @param id
     * @author pqk
     * @date 2022/07/17 16:13
     */
    DetailVo detail(Integer id);

    /**
     * @description 所有商品父子类
     * @return
     * @param
     * @author pqk
     * @date 2022/07/17 18:31
     */
    CatAndBrandVo catAndBrand();
    /**
     * @description 商品新增
     * @return  null
     * @param createBo
     * @author pqk
     * @date 2022/07/18 17:25
     */
    void create(CreateBo createBo);

    /**
     * @description 商品更新
     * @return
     * @param updateBo
     * @author pqk
     * @date 2022/07/18 21:34
     */
    void update(UpdateBo updateBo);

    /*商品列表*/
    IssueAndKeywordListVo list1(BaseParam param, MarketGoods marketGoods);
}
