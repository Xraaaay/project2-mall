package com.cskaoyan.controller.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.marketConfig.MarketSystemVO;
import com.cskaoyan.bean.system.MarketAdminListVo;
import com.cskaoyan.bean.system.MarketStorageListVo;
import com.cskaoyan.service.system.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对象存储
 * @since 2022/07/17 14:23
 * @author lyx
 */

@RestController
@RequestMapping("admin/storage/")
public class StorageController {
    @Autowired
    StorageService storageService;

    @GetMapping("list")
    public BaseRespVo list(BasePageInfo info, String name) {
        CommonData<MarketStorageListVo> marketStorage = storageService.list(info , name);

        return BaseRespVo.ok(marketStorage);
    }


}
