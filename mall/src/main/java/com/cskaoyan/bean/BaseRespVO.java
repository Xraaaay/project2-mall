package com.cskaoyan.bean;

import lombok.Data;

/**
 * @Description
 * @Author Xrw
 * @Date 2022/7/14 19:33
 */
@Data
public class BaseRespVO<T> {
    private T data;
    private Integer code;
    private String msg;


}
