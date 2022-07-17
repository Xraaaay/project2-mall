package com.cskaoyan.service.system;

import com.cskaoyan.bean.MarketStorage;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketStorageListVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lyx
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


    CommonData<MarketStorage> list(BasePageInfo info, String key, String value);

    MarketStorage update(MarketStorage marketStorage);

    void delete(MarketStorage marketStorage);
}
