package com.cskaoyan.controller.wx.cart;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.wx.cart.WxCartVO;
import com.cskaoyan.service.wx.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 * @since 2022/07/19 10:31
 * @author lyx
 */



@RestController
@RequestMapping("wx/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @RequestMapping("goodscount")
    public BaseRespVo goodsCountWx() {
        Integer goodscount = cartService.goodsCount();
        return BaseRespVo.ok(goodscount);
    }

    /**
     * 有点烦，按说库存等于0就不能加入到购物车，但是它给我的请求参数就没给
     * 库存参数啊。 是我想多了， 应该是。
     *还有要插入到数据库中，需要连接goods库 和 product库
     * lyx
     * @return
     */
    @RequestMapping("add")
    public BaseRespVo addWx(@RequestBody Map<String, Integer> map) {
        Integer goodscount = cartService.addWx(map);
        return BaseRespVo.ok(null);
    }

    @RequestMapping("index")
    public BaseRespVo indexWx() {
        WxCartVO wxCartVO = cartService.index();
        return BaseRespVo.ok(wxCartVO);
    }

    /**
     * 更新数量，但是好像还要和商品库存保持增减
     * 再外面修改好数量再uodate
     * lyx
     * @return
     */
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Map<String, Integer> map) {
        cartService.update(map);
        return BaseRespVo.ok(null);
    }



    @PostMapping("checked")
    //接收参数为什么用Mapper 而不用 list<interger> ,Interger ischecked
    public BaseRespVo checkedWx(@RequestBody Map<String, Object> map) {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        Integer isChecked = (Integer) map.get("isChecked");
        WxCartVO wxCartVO = cartService.checked(productIds,isChecked);
        // Request processing failed; nested exception is org.springframework.dao.DuplicateKeyException:
        // com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '140' for key 'PRIMARY'
        // ; Duplicate entry '140' for key 'PRIMARY'; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '140' for key 'PRIMARY'
        return BaseRespVo.ok(wxCartVO);
    }



    @RequestMapping("delete")
    public BaseRespVo deleteWx(@RequestBody Map<String, Object> map) {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        WxCartVO wxCartVO = cartService.delete(productIds);

        return BaseRespVo.ok(wxCartVO);
    }




}
