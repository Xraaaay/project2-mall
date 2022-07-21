package com.cskaoyan.controller.wx.goods;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.wx.goods.CategoryWxVo;
import com.cskaoyan.bean.wx.goods.detail.DetailWxVo;
import com.cskaoyan.bean.wx.goods.ListWxBo;
import com.cskaoyan.bean.wx.goods.PageInfoDataVo;
import com.cskaoyan.bean.wx.goods.detail.goodsdetail.GoodsDetailVO;
import com.cskaoyan.service.wx.goods.GoodsWxService;
import com.cskaoyan.service.wx.goods.goodsdetail.GoodsDetailWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pqk
 * @since 2022/07/19 20:10
 */
@RestController
@RequestMapping("wx/goods")
public class GoodsWxController {

    @Autowired
    GoodsWxService goodsWxService;

    @Autowired
    GoodsDetailWXService goodsDetailWXService;


    /**
     * listWxBo categoryId=1005010&page=1&limit=10
     * @description 种类类目
     * @return
     * @param listWxBo
     * @author pqk
     * @date 2022/07/19 21:00
     */
    @RequestMapping("list")
    public BaseRespVo list(ListWxBo listWxBo,String keyword,String sort,String order){
        PageInfoDataVo list = goodsWxService.list(listWxBo,keyword,sort,order);
        return BaseRespVo.ok(list);
    }

    /**
     * id=1008002
     * @description 商品类别
     * @author pqk
     * @date 2022/07/19 20:12
     */
    @RequestMapping("category")
    public BaseRespVo category(String id){
        CategoryWxVo categoryWxVo = goodsWxService.category(id);
        return BaseRespVo.ok(categoryWxVo);
    }
    
    /**
     * @description 商品总目
     * @author pqk
     * @date 2022/07/19 21:38 
     */
    @RequestMapping("count")
    public BaseRespVo count(){
        Long count =goodsWxService.count();
        return BaseRespVo.ok(count);
    }

    /**
     * id=1009024
     * @description 商品详情 //
     * @author pqk
     * @date 2022/07/19 23:19
     */
    @RequestMapping("detail")
    public BaseRespVo detail(String id){
        int i = Integer.parseInt(id);
        GoodsDetailVO detail = goodsDetailWXService.detail(Integer.parseInt(id));
        return BaseRespVo.ok(detail);
    }

    /**
     * id=1181089
     * @description 相关商品 //
     * @author pqk
     * @date 2022/07/20 14:02
     */
    @RequestMapping("related")
    public BaseRespVo related(String id){
        int i = Integer.parseInt(id);
        CommonData commonData = goodsWxService.related(i);
        return BaseRespVo.ok(commonData);
    }
}
