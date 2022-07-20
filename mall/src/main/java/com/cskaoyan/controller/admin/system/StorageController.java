package com.cskaoyan.controller.admin.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.common.MarketStorage;
import com.cskaoyan.service.admin.system.StorageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 对象存储
 *
 * @author lyx
 * @since 2022/07/17 14:23
 */

@RestController
@RequestMapping("admin/storage")
public class StorageController {
    @Autowired
    StorageService storageService;

    @RequiresPermissions("admin:storage:list")
    @RequestMapping("list")
    public BaseRespVo list(BasePageInfo info, String key, String name) {

        CommonData<MarketStorage> marketStoragess = storageService.list(info, key, name);

        return BaseRespVo.ok(marketStoragess);
    }

    /**
     * 文件上传到阿里云oss
     *
     * @param file
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/17 16:37
     */
    @RequiresPermissions("admin:storage:create")
    @PostMapping("/create")
    public BaseRespVo create(MultipartFile file) throws IOException {

        MarketStorage marketStorage;
        marketStorage = storageService.create(file);
        return BaseRespVo.ok(marketStorage);
    }

    @RequiresPermissions("admin:storage:update")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody MarketStorage marketStorage) {

        MarketStorage updateVo = storageService.update(marketStorage);
        return BaseRespVo.ok(updateVo);


    }

    @RequiresPermissions("admin:storage:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody MarketStorage marketStorage) {
        storageService.delete(marketStorage);
        return BaseRespVo.ok(null);
    }

}
