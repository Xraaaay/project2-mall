package com.cskaoyan.controller.wx.storage;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.MarketStorage;
import com.cskaoyan.service.admin.system.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 微信图片上传
 *
 * @author fanxing056
 * @date 2022/07/20 17:26
 */

@RestController
@RequestMapping("wx/storage/")
public class ImgUploadController {

    @Autowired
    StorageService storageService;

    /**
     * 微信上传图片
     *
     * @param file
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/20 17:29
     */
    @PostMapping("/upload")
    public BaseRespVo upload(MultipartFile file) throws IOException {

        MarketStorage marketStorage;
        marketStorage = storageService.create(file);
        return BaseRespVo.ok(marketStorage);
    }
}
