package com.hgsoft.modules.utils;

import io.swagger.models.auth.In;

public class ServiceTypeMapToNameUtil {
    private static String[] serviceTypeNames = {"填充无用", "高速公路", "停车场", "加油站", "服务区", "市政拓展"};

    public static String getServiceTypeName(Integer serviceType) {
        return serviceTypeNames[serviceType];
    }

}
