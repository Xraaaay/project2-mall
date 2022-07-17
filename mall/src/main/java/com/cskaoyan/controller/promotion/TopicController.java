package com.cskaoyan.controller.promotion;

import com.cskaoyan.bean.MarketTopic;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.promotion.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 专题管理
 *
 * @author fanxing056
 * @date 2022/07/16 20:59
 */

@RestController
@RequestMapping("/admin/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    /**
     * 获取专题列表，分页
     *
     * @param pageInfo
     * @param title
     * @param subtitle
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 21:13
     */
    @GetMapping("/list")
    public BaseRespVo list(BasePageInfo pageInfo, String title, String subtitle) {

        CommonData<MarketTopic> commonData = topicService.list(pageInfo, title, subtitle);

        return BaseRespVo.ok(commonData);
    }

    /**
     * 获取专题详情信息，专题相关商品信息
     * 用于修改专题时数据回显
     *
     * @param id
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 22:05
     */
    @GetMapping("/read")
    public BaseRespVo read(Integer id) {

        Map<String, Object> map = topicService.read(id);

        return BaseRespVo.ok(map);
    }

    /**
     * 添加专题
     *
     * @param topic
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 22:14
     */
    @PostMapping("/create")
    public BaseRespVo create(@RequestBody MarketTopic topic) {

        try {
            topicService.create(topic);
        } catch (Exception e) {
            return BaseRespVo.invalidData();
        }
        return BaseRespVo.ok(topic);
    }

    /**
     * 修改专题
     *
     * @param topic
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 22:39
     */
    @PostMapping("/update")
    public BaseRespVo update(@RequestBody MarketTopic topic) {

        try {
            topicService.update(topic);
        } catch (Exception e) {
            return BaseRespVo.invalidData();
        }

        return BaseRespVo.ok(topic);
    }

    /**
     * 逻辑删除专题
     *
     * @param topic
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 22:45
     */
    @PostMapping("/delete")
    public BaseRespVo delete(@RequestBody MarketTopic topic) {

        int affect = topicService.delete(topic);
        if (affect == 0) {
            return BaseRespVo.invalidData("参数异常");
        }

        return BaseRespVo.ok(null);
    }

    /**
     * 批量(逻辑)删除专题
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 23:00
     */
    @PostMapping("/batch-delete")
    public BaseRespVo batchDelete(@RequestBody Map<String, List<Integer>> map) {

        int affect = topicService.batchDelete(map);
        return BaseRespVo.ok(null);
    }
}
