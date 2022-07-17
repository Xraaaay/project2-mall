package com.cskaoyan.controller.goods;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.goodsVo.CatAndBrandVo;
import com.cskaoyan.bean.goodsVo.DetailVo;
import com.cskaoyan.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品管理模块：商品列表
 * @author pqk
 * @since 2022/07/16 20:50
 */
@RestController
@RequestMapping("admin/goods")
public class goodsController {

    @Autowired
    GoodsService goodsService;

    /**
     * page=1&limit=20&sort=add_time&order=desc
     *
     * @description 展示商品信息
     * @author pqk
     * @date 2022/07/16 21:18
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer page,Integer limit,String sort,String order){
        CommonData commonData = goodsService.list(page,limit,sort,order);
        return BaseRespVo.ok(commonData);
    }
    
    /**
     * @description 删除指定商品
     * @author pqk
     * @date 2022/07/16 22:37 
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map){
        Integer id = (Integer) map.get("id");
        goodsService.delete(id);
        return BaseRespVo.ok(null);
    }

    /**
     * @description 商品编辑 todo 返回格式的问题
     * @author pqk
     * @date 2022/07/16 23:25
     */
    @RequestMapping("detail")
    public BaseRespVo detail(Integer id){
        DetailVo detailVo = goodsService.detail(id);
        return BaseRespVo.ok(detailVo);
    }
    
    /**
     * @description
     * @author pqk
     * @date 2022/07/17 18:02 
     */
    @RequestMapping("catAndBrand")
    public BaseRespVo catAndBrand(){
        CatAndBrandVo catAndBrandVo = goodsService.catAndBrand();

        return BaseRespVo.ok(catAndBrandVo);
    }
}
