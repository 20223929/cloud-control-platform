package com.hgsoft.modules.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 维度常量类
 * Created by 吴鉴武 on 2021/6/16 11:21
 */
public class Dimension {

    private static Map<Integer,String> DIMENSION_MAP;
    static {
        DIMENSION_MAP = new HashMap<>();
        DIMENSION_MAP.put(1,"%Y");
        DIMENSION_MAP.put(2,"%Y-%m");
        DIMENSION_MAP.put(3,"%Y-%m-%d");
        DIMENSION_MAP.put(4,"%Y-%m-%d");
    }

    public static String getDimension(Integer value){
        return DIMENSION_MAP.get(value);
    }
}
