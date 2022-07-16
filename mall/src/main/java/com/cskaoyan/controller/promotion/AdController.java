package com.cskaoyan.controller.promotion;

/**
 * 广告管理
 *
 * @author fanxing056
 * @date 2022/07/16 09:19
 */

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.MarketAd;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.promotion.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/ad")
public class AdController {

    @Autowired
    AdService adService;

    /**
     * 新增广告
     * TODO:等待admin/storage/create接口完成@lyx
     *
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 14:43
     */
    @PostMapping("/create")
    public BaseRespVo create(@RequestBody MarketAd ad) {

        try {
            adService.create(ad);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVo.invalidData("创建失败");
        }

        return BaseRespVo.ok(ad);
    }

    /**
     * 获取广告列表
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 14:43
     */
    @GetMapping("/list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order, String name, String content) {

        CommonData<MarketAd> data = adService.query(page, limit, sort, order, name, content);

        return BaseRespVo.ok(data);
    }

    /**
     * 逻辑删除一条广告
     *
     * @param ad
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 15:27
     */
    @PostMapping("/delete")
    public BaseRespVo delete(@RequestBody MarketAd ad) {

        try {
            adService.delete(ad);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVo.invalidData();
        }
        return BaseRespVo.ok(null);
    }

    /**
     * 更改一条广告
     *
     * @param ad
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 15:39
     */
    @PostMapping("/update")
    public BaseRespVo update(@RequestBody MarketAd ad) {

        try {
            adService.update(ad);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVo.invalidData();
        }
        return BaseRespVo.ok(ad);
    }
}
