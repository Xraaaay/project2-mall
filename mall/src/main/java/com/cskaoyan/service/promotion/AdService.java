package com.cskaoyan.service.promotion;

import com.cskaoyan.bean.MarketAd;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;


/**
 * 广告
 *
 * @author fanxing056
 * @date 2022/07/16 14:08
 */

public interface AdService {

    /**
     * 获取广告列表，分页
     *
     * @param pageInfo
     * @param name
     * @param content
     * @return com.cskaoyan.bean.common.CommonData<com.cskaoyan.bean.MarketAd>
     * @author fanxing056
     * @date 2022/07/16 15:31
     */
    CommonData<MarketAd> query(BasePageInfo pageInfo, String name, String content);

    /**
     * 新增一条广告
     *
     * @param ad
     * @return int
     * @author fanxing056
     * @date 2022/07/16 14:47
     */
    int create(MarketAd ad);

    /**
     * 逻辑删除一条广告
     *
     * @param ad
     * @return int
     * @author fanxing056
     * @date 2022/07/16 15:12
     */
    int delete(MarketAd ad);

    /**
     * 更改一条广告
     *
     * @param ad
     * @return int
     * @author fanxing056
     * @date 2022/07/16 15:40
     */
    int update(MarketAd ad);
}
