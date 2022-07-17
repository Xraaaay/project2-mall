package com.cskaoyan.service.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketStorageListVo;

/**
 * 
 * @since 2022/07/17 14:28
 * @author lyx
 */
public interface StorageService {
    CommonData<MarketStorageListVo> list(BasePageInfo info, String name);
}
