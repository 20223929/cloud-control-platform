package com.hgsoft.modules.querymanage.enums;

import lombok.Getter;

/**
 * 车牌颜色枚举
 * Created by 吴鉴武 on 2021/5/7 8:59
 */
public enum VehicleColor{

    BLUE(0,"蓝色"),
    YELLOW(1,"黄色"),
    BLACK(2,"黑色"),
    WHITE(3,"白色"),
    GRADUAL_GREEN(4,"渐变绿色"),
    YELLOW_GREEN(5,"黄绿相双拼色"),
    BLUE_WHITE_GRADUAL(6,"蓝白渐变色"),
    TEMPORARY_LICENCE(7,"临时牌照"),
    GREEN(11,"绿色"),
    RED(12,"红色");


    @Getter
    private Integer colorNumber;
    @Getter
    private String colorDesc;
    VehicleColor(Integer colorNumber,String colorDesc){
        this.colorNumber = colorNumber;
        this.colorDesc = colorDesc;
    }

    /**
     * 根据车牌颜色数字获取车牌颜色描述
     * @param colorNumber
     * @return
     */
    public static String getColorDescByColorNumber(Integer colorNumber){
        if (colorNumber == null) return null;
        for (VehicleColor vehicleColor : VehicleColor.values()){
            if(vehicleColor.colorNumber.equals(colorNumber)){
                return vehicleColor.colorDesc;
            }
        }
        return "未知颜色";
    }
}
