package com.cskaoyan.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 利用反射，将对象转换成Map
 * @since 2022/07/16 20:49
 * @author lyx
 */
public class BeanToMapUtil {
    public static Map beanToMap(Object object) throws IllegalAccessException {
        HashMap<String , Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }

        return  map;

    }


}
