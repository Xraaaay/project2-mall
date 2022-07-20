package com.cskaoyan.controller.wx.cart;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.wx.cart.WxCartVO;
import com.cskaoyan.controller.wx.auth.WxAuthController;
import com.cskaoyan.service.wx.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author lyx
 * @since 2022/07/19 10:31
 */
@RestController
@RequestMapping("wx/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("index")
    public BaseRespVo indexWx() {
        Map<String, Object> index = cartService.index();
        if (index == null) {
            return WxAuthController.unAuthc();
        }
        return BaseRespVo.ok(index);
    }

    @PostMapping("checked")
    public BaseRespVo checkedWx(@RequestBody Map<String, Object> map) {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        Integer isChecked = (Integer) map.get("isChecked");
        Map<String, Object> index = cartService.checked(productIds, isChecked);
        return BaseRespVo.ok(index);
    }

    /**
     * 更新数量，但是好像还要和商品库存保持增减
     * 再外面修改好数量再uodate
     * lyx
     *
     * @return
     */
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Map<String, Integer> map) {
        int msg = cartService.update(map);
        if (msg == 200){
            return BaseRespVo.ok("添加成功");
        } else if (msg == 710) {
            return BaseRespVo.invalidNumber("库存不足");
        }else {
            return BaseRespVo.ok("更新失败");
        }
    }

    @PostMapping("delete")
    public BaseRespVo deleteWx(@RequestBody Map<String, Object> map) {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        Map<String, Object> index = cartService.delete(productIds);
        return BaseRespVo.ok(index);
    }

    @GetMapping("goodscount")
    public BaseRespVo goodsCountWx() {
        Integer goodsCount = cartService.goodsCount();
        return BaseRespVo.ok(goodsCount);
    }

    /**
     * 有点烦，按说库存等于0就不能加入到购物车，但是它给我的请求参数就没给
     * 库存参数啊。 是我想多了， 应该是。
     * 还有要插入到数据库中，需要连接goods库 和 product库
     * lyx
     *
     * @return
     */
    @RequestMapping("add")
    public BaseRespVo addWx(@RequestBody Map<String, Integer> map) {
        Integer goodsCount = cartService.addWx(map);
        if (goodsCount == 200){
            return BaseRespVo.ok("添加成功");
        } else if (goodsCount == 710) {
            return BaseRespVo.invalidNumber("库存不足");
        }else {
            return BaseRespVo.ok("插入异常");
        }
    }
}
