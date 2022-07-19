package com.cskaoyan.bean.admin.mallmanagement;

import lombok.Data;

/**
 * 响应：成功、失败
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
    public static <T> BaseRespSuccessVo failed(T data) {
        BaseRespSuccessVo baseRespSuccessVo = new BaseRespSuccessVo();
        baseRespSuccessVo.setErrno(10000);
        baseRespSuccessVo.setData(data);
        baseRespSuccessVo.setErrmsg("失败");
        return baseRespSuccessVo;
    }
}
