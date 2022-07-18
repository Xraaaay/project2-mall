package com.cskaoyan.controller.wx.topic;

import com.cskaoyan.bean.MarketGoods;
import com.cskaoyan.bean.MarketTopic;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.promotion.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 小程序专题
 *
 * @author fanxing056
 * @date 2022/07/18 21:20
 */

@RestController
@RequestMapping("/wx/topic")
public class TopicControllerWX {

    @Autowired
    TopicService topicService;

    /**
     * 专题详情
     *
     * @param id
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/18 21:35
     */
    @GetMapping("/detail")
    public BaseRespVo detail(Integer id) {

        Map<String, Object> detail = topicService.read(id);
        List<MarketGoods> goodsList = (List<MarketGoods>) detail.remove("goodsList");
        detail.put("goods", goodsList);
        return BaseRespVo.ok(detail);
    }

    /**
     * 相关专题：专题推荐
     *
     * @param id
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/18 21:47
     */
    @GetMapping("/related")
    public BaseRespVo related(Integer id) {

        CommonData<MarketTopic> commonData = topicService.related(id);

        return BaseRespVo.ok(commonData);
    }

    /**
     * 获取专题列表
     *
     * @param pageInfo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/18 22:03
     */
    @GetMapping("/list")
    public BaseRespVo list(BasePageInfo pageInfo) {

        pageInfo.setSort("add_time");
        pageInfo.setOrder("desc");
        CommonData<MarketTopic> commonData = topicService.list(pageInfo, null, null);
        return BaseRespVo.ok(commonData);
    }
}
