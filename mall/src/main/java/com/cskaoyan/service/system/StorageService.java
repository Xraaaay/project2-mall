package com.cskaoyan.service.system;

import com.cskaoyan.bean.MarketStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lyx
 * @since 2022/07/17 14:28
 */
public interface StorageService {

    /**
     * 文件上传
     *
     * @param file
     * @return com.cskaoyan.bean.MarketStorage
     * @author fanxing056
     * @date 2022/07/17 15:16
     */
    MarketStorage create(MultipartFile file) throws IOException;
}
