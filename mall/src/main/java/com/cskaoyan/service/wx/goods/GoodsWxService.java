package com.cskaoyan.service.wx.goods;



import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.wx.goods.CategoryWxVo;
import com.cskaoyan.bean.wx.goods.ListWxBo;
import com.cskaoyan.bean.wx.goods.PageInfoDataVo;
import com.cskaoyan.bean.wx.goods.detail.DetailWxVo;

/**
 * @author pqk
 * @since 2022/07/19 20:14
 */

public interface GoodsWxService {
    /**
     * @description 商品分类
     * @return 三种分类的Vo
     * @param id
     * @author pqk
     * @date 2022/07/19 20:40
     */
    CategoryWxVo category(String id);

    /**
     * @description 商品列表
     * @return
     * @param listWxBo
     * @param keyword
     * @param sort
     * @param order
     * @author pqk
     * @date 2022/07/19 21:13
     */
    PageInfoDataVo list(ListWxBo listWxBo, String keyword, String sort, String order);

    /**
    * @description 所有商品数量
    * @return 总数
    * @param
    * @author pqk
    * @date 2022/07/19 21:39
     */
    Long count();

    /***
     *  返回相关商品
     * @param i 商品id
     * @return com.cskaoyan.bean.common.CommonData
     * @author pqk
     * @since 2022/07/20 14:10
     */
    CommonData related(int i);


    DetailWxVo detail(int i);
}
