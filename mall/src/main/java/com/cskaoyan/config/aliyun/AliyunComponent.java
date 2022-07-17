package com.cskaoyan.config.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * 阿里云组件，用于oss（对象存储）和sms（短信服务）
 *
 * @author fanxing056
 * @date 2022/07/17 14:46
 */

@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunComponent {
    String accessKeyId;
    String accessKeySecret;

    Oss oss;
    Sms sms;

    public OSSClient getOssClient() {
        OSSClient ossClient = new OSSClient(oss.getEndPoint(), accessKeyId, accessKeySecret);
        return ossClient;
    }

    // 文件上传
    public PutObjectResult fileUpload(String fileName, File file) {
        OSSClient ossClient = getOssClient();
        PutObjectResult putObjectResult = ossClient.
                putObject(oss.getBucket(), fileName, file);
        return putObjectResult;
    }

    // 文件上传
    public PutObjectResult fileUpload(String fileName, InputStream inputStream) {
        OSSClient ossClient = getOssClient();
        PutObjectResult putObjectResult = ossClient.
                putObject(oss.getBucket(), fileName, inputStream);
        return putObjectResult;
    }

    // 发送短信
    public CommonResponse sendMsg(String phoneNumber, String code) {
        String signName = sms.getSignName();
        String templateCode = sms.getTemplateCode();


        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(sms.getDomain());
        request.setVersion(sms.getVersion());
        request.setAction(sms.getAction());

        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
}
