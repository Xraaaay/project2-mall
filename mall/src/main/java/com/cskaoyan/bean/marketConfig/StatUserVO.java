package com.cskaoyan.bean.marketConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 
 * @since 2022/07/18 09:42
 * @author lyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatUserVO {


    /**
     * columns : ["day","users"]
     * rows : [{"day":"2019-04-20","users":1},{"day":"2022-07-17","users":4}]
     */
    private List<String> columns;
    private List<StatRowsVO> rows;


}
