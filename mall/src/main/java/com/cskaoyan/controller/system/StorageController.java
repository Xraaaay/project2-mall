package com.cskaoyan.controller.system;

import com.cskaoyan.bean.MarketStorage;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.system.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



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

    @RequestMapping("list")
    public BaseRespVo list(BasePageInfo info, String key, String name) {

           CommonData<MarketStorage> marketStoragess = storageService.list(info,key,name);

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
    @PostMapping("/create")
    public BaseRespVo create(MultipartFile file) {

        MarketStorage marketStorage = null;
        try {
            marketStorage = storageService.create(file);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVo.invalidData("参数异常");
        }
        return BaseRespVo.ok(marketStorage);
    }

    @RequestMapping("update")
    public BaseRespVo update(@RequestBody MarketStorage marketStorage) {

        MarketStorage updateVo = storageService.update(marketStorage);
        return BaseRespVo.ok(updateVo);


    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody MarketStorage marketStorage) {
        storageService.delete(marketStorage);
        return BaseRespVo.ok(null);
    }

}
