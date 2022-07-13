package com.project.bean;

import lombok.Data;

@Data
public class BaseRespVO<T> {
    private T data;
    private Integer code;
    private String msg;

    public static BaseRespVO ok() {
        return ok(null);
    }

    public static <T> BaseRespVO<T> ok(T data) {
        BaseRespVO<T> baseRespVO = new BaseRespVO<>();
        baseRespVO.setCode(200);
        baseRespVO.setMsg("OK");
        baseRespVO.setData(data);
        return baseRespVO;
    }

    public static BaseRespVO error(String msg) {
        BaseRespVO baseRespVO = new BaseRespVO();
        baseRespVO.setCode(500);
        baseRespVO.setMsg(msg);
        return baseRespVO;
    }
}
