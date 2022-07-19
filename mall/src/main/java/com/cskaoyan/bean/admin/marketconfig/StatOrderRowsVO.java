package com.cskaoyan.bean.admin.marketconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @since 2022/07/18 15:07
 * @author lyx
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatOrderRowsVO {


    /**
     * amount : 395.0
     * orders : 1
     * customers : 1
     * day : 2022-07-16
     * pcr : 395.0
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private double amount;
    private int orders;
    private int customers;
    private String day;
    private double pcr;


}
