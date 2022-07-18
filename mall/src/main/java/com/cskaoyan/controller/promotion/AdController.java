package com.cskaoyan.controller.promotion;


import com.cskaoyan.bean.MarketAd;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.promotion.AdService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/create")
    public BaseRespVo create(@RequestBody @Validated MarketAd ad) {

        // 正则验证，验证广告链接
        if (!ad.getLink().matches("[a-zA-z]+://[^\\s]*") && !ad.getLink().matches("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?")) {
            return BaseRespVo.invalidData("请输入正确的活动链接");
        }

        try {
            adService.create(ad);
        } catch (Exception e) {
            return BaseRespVo.invalidData("创建失败");
        }

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

        try {
            adService.delete(ad);
        } catch (Exception e) {
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

        // 正则验证，验证广告链接
        if (!ad.getLink().matches("[a-zA-z]+://[^\\s]*") && !ad.getLink().matches("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?")) {
            return BaseRespVo.invalidData("请输入正确的活动链接");
        }

        try {
            adService.update(ad);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVo.invalidData();
        }
        return BaseRespVo.ok(ad);
    }
}
