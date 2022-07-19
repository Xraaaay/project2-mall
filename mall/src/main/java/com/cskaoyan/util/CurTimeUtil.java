package com.cskaoyan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Zah
 * @date 2022/07/19 15:08
 */
public class CurTimeUtil {

    /**
     * 获取当前日期 yyyy-MM-dd HH:mm:ss
     * @return java.lang.String
     * @author Zah
     * @date 2022/07/19 15:12 
     */
    public static String getCurTime(){

        // 小写的hh取得12小时，大写的HH取得24小时
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
