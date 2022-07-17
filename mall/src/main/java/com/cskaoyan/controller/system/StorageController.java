package com.cskaoyan.controller.system;

import com.cskaoyan.bean.MarketStorage;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.service.system.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 对象存储
 *
 * @author lyx
 * @since 2022/07/17 14:23
 */

@RestController
@RequestMapping("admin/storage/")
public class StorageController {
    @Autowired
    StorageService storageService;
    //
    // @RequestMapping("list")
    // public BaseRespVo list() {
    //
    //
    //     return BaseRespVo.ok();
    // }

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

}
