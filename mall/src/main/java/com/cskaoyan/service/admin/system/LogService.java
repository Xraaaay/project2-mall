package com.cskaoyan.service.admin.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.system.MarketLog;

/**
 * 系统管理模块：操作日志
 * @author Xrw
 * @date 2022/7/16 16:15
 */
public interface LogService {
    /**
     * 显示操作日志列表
     * @param info 分页信息
     * @param name 操作管理员名称
     * @return 分页信息和操作日志列表
     * @author Xrw
     * @date 2022/7/16 16:18
     */
    CommonData<MarketLog> list(BasePageInfo info, String name);
}
