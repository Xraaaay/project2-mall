package com.cskaoyan.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stone
 * @date 2022/01/06 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRespVo<T> {
    T data;
    int errno;
    String errmsg;



    public static <T> BaseRespVo ok(T data) {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    public static <T> BaseRespVo invalidData(String msg) {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(504);
        baseRespVo.setErrmsg(msg);
        return baseRespVo;
    }
    public static <T> BaseRespVo invalidData() {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(504);
        baseRespVo.setErrmsg("更新数据已失效，请刷新页面重新操作");
        return baseRespVo;
    }
    public static <T> BaseRespVo invalidParameter(String msg) {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(400);
        baseRespVo.setErrmsg(msg);
        return baseRespVo;
    }

    public static <T> BaseRespVo invalidNumber(String msg) {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(711);
        baseRespVo.setErrmsg(msg);
        return baseRespVo;
    }

    public static <T> BaseRespVo unAuthc() {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(502);
        baseRespVo.setErrmsg("认证失败");
        return baseRespVo;
    }
    public static BaseRespVo noPermission() {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(506);
        baseRespVo.setErrmsg("无操作权限");
        return baseRespVo;
    }
}
