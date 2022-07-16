package com.cskaoyan.bean.mallManagement;

import lombok.Data;

/**
 * 响应：成功
 * @author shn
 * @date 2022/07/16 16:23
 */
@Data
public class BaseRespSuccessVo<T> {
    int errno;
    T data;
    String errmsg;

    public static <T> BaseRespSuccessVo success(T data) {
        BaseRespSuccessVo baseRespSuccessVo = new BaseRespSuccessVo();
        baseRespSuccessVo.setErrno(0);
        baseRespSuccessVo.setData(data);
        baseRespSuccessVo.setErrmsg("成功");
        return baseRespSuccessVo;
    }
}
