package com.cskaoyan.config.aliyun;

import lombok.Data;

/**
 * Sms参数类
 * @date 2022/07/17 14:48
 * @author fanxing056
 */

@Data
public class Sms {

    String signName;
    String templateCode;

    String domain;
    String version;
    String Action;
}
