package com.cskaoyan.controller.promotion;


import com.cskaoyan.anno.ParamValidation;
import com.cskaoyan.bean.MarketAd;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.promotion.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 广告管理
 *
 * @author fanxing056
 * @date 2022/07/16 09:19
 */
@RestController
@RequestMapping("admin/ad")
public class AdController {

    @Autowired
    AdService adService;

    /**
     * 获取广告列表
     *
     * @param pageInfo
     * @param name
     * @param content
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/17 20:38
     */
    @GetMapping("/list")
    public BaseRespVo list(BasePageInfo pageInfo, String name, String content) {

        CommonData<MarketAd> data = adService.query(pageInfo, name, content);
        return BaseRespVo.ok(data);
    }

    /**
     * 新增广告
     *
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 14:43
     */
    @ParamValidation
    @PostMapping("/create")
    public BaseRespVo create(@RequestBody @Validated MarketAd ad, BindingResult bindingResult) {

        adService.create(ad);
        return BaseRespVo.ok(ad);
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

        adService.delete(ad);
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
    @ParamValidation
    @PostMapping("/update")
    public BaseRespVo update(@RequestBody @Validated MarketAd ad, BindingResult bindingResult) {

        adService.update(ad);
        return BaseRespVo.ok(ad);
    }
}
