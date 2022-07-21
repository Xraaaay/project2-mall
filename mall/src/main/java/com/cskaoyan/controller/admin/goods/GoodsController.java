package com.cskaoyan.controller.admin.goods;

import com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.goods.vo.CatAndBrandVo;
import com.cskaoyan.bean.admin.goods.bo.CreateBo;
import com.cskaoyan.bean.admin.goods.vo.DetailVo;
import com.cskaoyan.bean.admin.goods.bo.UpdateBo;
import com.cskaoyan.bean.common.MarketGoods;
import com.cskaoyan.service.admin.goods.GoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    /**
     * page=1&limit=20&sort=add_time&order=desc
     *
     * @description 展示商品信息
     * @author pqk
     * @date 2022/07/16 21:18
     */
    //@RequiresPermissions("admin:goods:list")
    //@RequestMapping("list")
    public BaseRespVo list(Integer page,Integer limit,String sort,String order){
        CommonData commonData = goodsService.list(page,limit,sort,order);
        return BaseRespVo.ok(commonData);
    }

    /**
     * 商品列表
     * @param param
     * @param marketGoods
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author shn
     * @date 2022/7/20 17:32
     */
    @RequiresPermissions("admin:goods:list")
    @GetMapping("list")
    public BaseRespSuccessVo list1(BaseParam param, MarketGoods marketGoods, Integer goodsId){
        marketGoods.setId(goodsId);
        IssueAndKeywordListVo listVo=goodsService.list1(param,marketGoods);
        return BaseRespSuccessVo.success(listVo);
    }

    /**
     * @description 删除指定商品
     * @author pqk
     * @date 2022/07/16 22:37 
     */
    @RequiresPermissions("admin:goods:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map){
        Integer id = (Integer) map.get("id");
        goodsService.delete(id);
        return BaseRespVo.ok(null);
    }

    /**
     * @description 商品编辑
     * @author pqk
     * @date 2022/07/16 23:25
     */
    @RequestMapping("detail")
    public BaseRespVo detail(Integer id){
        DetailVo detailVo = goodsService.detail(id);
        return BaseRespVo.ok(detailVo);
    }
    
    /**
     * @description 商品类目
     * @author pqk
     * @date 2022/07/17 18:02 
     */
    @RequestMapping("catAndBrand")
    public BaseRespVo catAndBrand(){
        CatAndBrandVo catAndBrandVo = goodsService.catAndBrand();
        return BaseRespVo.ok(catAndBrandVo);
    }

    /**
     * @description 商品上架
     * @author pqk
     * @date 2022/07/18 14:56
     * 注：🐖 我必须给goodsid赋值 因为id传进来为null id在数据库也是自增的
     */
    @RequiresPermissions("admin:goods:create")
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody CreateBo createBo) {
        goodsService.create(createBo);
        return BaseRespVo.ok(null);
    }

    /**
     * @description
     * @author pqk
     * @date 2022/07/18 21:32
     */
    @RequiresPermissions("admin:goods:update")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody UpdateBo updateBo){
        goodsService.update(updateBo);
        return BaseRespVo.ok(null);
    }
}
