package com.cskaoyan.service.admin.goods;

import com.cskaoyan.bean.common.CommonData;

import java.util.Map;

/**
 * @author pqk
 * @since 2022/07/17 23:21
 */

public interface CommentService {
    /**
     * @description 所有评论
     * @return
     * @param
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @author pqk
     * @date 2022/07/17 23:26
     */
    CommonData list(Integer page, Integer limit, String sort, String order);

    /**
     * @description 删除指定评论
     * @return
     * @param map
     * @author pqk
     * @date 2022/07/18 0:13
     */
    void delete(Map map);
}
