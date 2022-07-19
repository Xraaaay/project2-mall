package com.cskaoyan.controller.wx.goods;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.wx.goods.CategoryWxVo;
import com.cskaoyan.bean.wx.goods.ListWxBo;
import com.cskaoyan.bean.wx.goods.PageInfoDataVo;
import com.cskaoyan.service.wx.goods.GoodsWxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pqk
 * @since 2022/07/19 20:10
 */
@RestController
@RequestMapping("wx/goods")
public class GoodsWxController {

    @Autowired
    GoodsWxService goodsWxService;


    /**
     * listWxBo categoryId=1005010&page=1&limit=10
     * @description 种类类目
     * @return
     * @param listWxBo
     * @author pqk
     * @date 2022/07/19 21:00
     */
    @RequestMapping("list")
    public BaseRespVo list(ListWxBo listWxBo){
        PageInfoDataVo list = goodsWxService.list(listWxBo);
        return BaseRespVo.ok(list);
    }

    /**
     * id=1008002
     * @description 商品类别 todo 待验证
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

}
