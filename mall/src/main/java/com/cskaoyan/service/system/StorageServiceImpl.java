package com.cskaoyan.service.system;

import com.aliyun.oss.model.PutObjectResult;
import com.cskaoyan.bean.MarketStorage;
import com.cskaoyan.config.aliyun.AliyunComponent;
import com.cskaoyan.mapper.MarketStorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author lyx
 * @since 2022/07/17 14:29
 */

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    AliyunComponent aliyunComponent;

    @Autowired
    MarketStorageMapper storageMapper;

    /**
     * 文件上传到阿里云oss
     *
     * @param file
     * @return com.cskaoyan.bean.MarketStorage
     * @author fanxing056
     * @date 2022/07/17 16:35
     */
    @Transactional
    @Override
    public MarketStorage create(MultipartFile file) throws IOException {

        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        // 文件大小
        long size = file.getSize();
        // 正文类型  image/jpeg
        String contentType = file.getContentType();

        // 获取文件后缀
        String[] split = originalFilename.split("\\.");
        String suffix = "";
        if (split != null && split.length > 1) {
            suffix = split[split.length - 1];
        }

        // 上传到阿里云
        // 生成随机文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        // 上传
        PutObjectResult putObjectResult = aliyunComponent.fileUpload(fileName, file.getInputStream());

        // 写入数据库文件存储表
        MarketStorage marketStorage = new MarketStorage();
        marketStorage.setKey(fileName);
        marketStorage.setName(originalFilename);
        marketStorage.setType(contentType);
        marketStorage.setSize((int) size);
        marketStorage.setUrl("https://" + aliyunComponent.getOss().getBucket() + "." +
                aliyunComponent.getOss().getEndPoint() + "/" + fileName);
        marketStorage.setAddTime(new Date());
        marketStorage.setUpdateTime(new Date());
        marketStorage.setDeleted(false);

        // 插入并获取主键id
        storageMapper.insert(marketStorage);
        return marketStorage;
    }
}
