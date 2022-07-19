package com.cskaoyan.bean.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketComment {
    private Integer id;

    private Integer valueId;

    private Byte type;

    private String content;

    private String adminContent;

    private Integer userId;

    private Boolean hasPicture;

    private String[] picUrls;

    private Short star;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT:+8")
    private Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Boolean deleted;


}