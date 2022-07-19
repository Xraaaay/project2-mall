package com.cskaoyan.service.admin.dashboard;


import java.util.Map;

/**
 * 小程序首页
 *
 * @author fanxing056
 * @date 2022/07/18 17:03
 */

public interface HomeService {

    /**
     * 小程序首页
     *
     * @return com.cskaoyan.bean.common.CommonData<java.util.Map < java.lang.String, java.lang.Object>>
     * @author fanxing056
     * @date 2022/07/18 17:07
     */
    Map<String, Object> index();
}
