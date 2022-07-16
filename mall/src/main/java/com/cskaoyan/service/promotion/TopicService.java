package com.cskaoyan.service.promotion;

import com.cskaoyan.bean.MarketTopic;
import com.cskaoyan.bean.common.CommonData;

import java.util.List;
import java.util.Map;

/**
 * 专题管理
 *
 * @author fanxing056
 * @date 2022/07/16 21:01
 */

public interface TopicService {

    /**
     * 获取专题列表
     *
     * @param page
     * @param limit
     * @param title
     * @param subtitle
     * @param sort
     * @param order
     * @return com.cskaoyan.bean.common.CommonData<com.cskaoyan.bean.MarketTopic>
     * @author fanxing056
     * @date 2022/07/16 21:06
     */
    CommonData<MarketTopic> list(Integer page, Integer limit, String title, String subtitle, String sort, String order);

    /**
     * 根据id获取专题信息，以及专题相关商品信息
     *
     * @param id
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author fanxing056
     * @date 2022/07/16 22:04
     */
    Map<String, Object> read(Integer id);

    /**
     * 添加专题
     *
     * @param topic
     * @return int
     * @author fanxing056
     * @date 2022/07/16 22:11
     */
    int create(MarketTopic topic);

    /**
     * 修改专题
     *
     * @param topic
     * @return int
     * @author fanxing056
     * @date 2022/07/16 22:20
     */
    int update(MarketTopic topic);

    /**
     * 逻辑删除专题
     *
     * @param topic
     * @return int
     * @author fanxing056
     * @date 2022/07/16 22:40
     */
    int delete(MarketTopic topic);

    /**
     * 批量（逻辑），删除专题
     *
     * @param map
     * @return int
     * @author fanxing056
     * @date 2022/07/16 22:53
     */
    int batchDelete(Map<String, List<Integer>> map);
}
