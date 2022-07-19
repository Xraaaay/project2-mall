package com.cskaoyan.bean.admin.marketconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @since 2022/07/18 09:47
 * @author lyx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor


public class StatRowsVO {

    /**
     * day : 2019-04-20
     * users : 1
     */
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String day;
    private int users;

}
