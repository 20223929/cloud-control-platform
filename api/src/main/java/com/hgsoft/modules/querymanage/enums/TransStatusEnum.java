package com.hgsoft.modules.querymanage.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 清分结果流水状态枚举
 * Created by 郑裕强 on 2022/5/10 14:52
 */
@RequiredArgsConstructor
public enum TransStatusEnum {

    NORMAL("0","正常记账"),
    TAC_CHECk_NOT_PASS("1","TAC验证未通过"),
    REPEAT_TRADE("2","重复交易"),
    USER_STATUS_CHANGE("3","用户状态变化"),
    INVALID_SERVER("4","无效服务类型"),
    OVERDUE("5","逾期超过设定值"),
    TRADE_ERROR("6","交易数据域错"),
    OVER_MAX_TRADE("7","超过最大交易限额"),
    CARD_NOT_EXIST("8","卡号不存在"),
    WAIT_SETTLE("9","卡状态不匹配"),
    CARD_STATUS_NOT_MATCH("10","卡超过有效期"),
    NOT_ALLOW_TRANS("11","不允许的交易"),
    CARD_CSN_NOT_MATCH("12","卡片CSN不匹配"),
    TEST_TRANS("13","测试交易"),
    CARD_ACCOUNT_NOT_MATCH("14","卡账不符"),
    INVALID_CARD_TYPE("15","无效卡类型"),
    LANE_TIME_ERROR("16","车道对时错误"),
    ETC_NOT_MATCH("17","ETC通行记录与过车图像未匹配"),
    VEHICLE_IMAGE_NOT_MATCH("18","过车识别图像校核有误"),
    PATH_DATA_ERROR("19","路径拟合数据的支撑证据有误");

    private final String value;
    private final String title;

    public String getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static String getTitleByValue(String value){
        if (value == null) return null;
        if (value.length() == 1) {
            for (TransStatusEnum transStatusEnum : TransStatusEnum.values()) {
                if(transStatusEnum.value.equals(value)) return transStatusEnum.title;
            }
        } else {
            StringBuffer titles = new StringBuffer();
            List<String> list = Arrays.stream(value.split(",")).collect(Collectors.toList());
            for (String s : list) {
                for (TransStatusEnum transStatusEnum : TransStatusEnum.values()) {
                    if (transStatusEnum.value.equals(s)) {
                        if (list.get(list.size()-1).equals(s)) {
                            titles.append(transStatusEnum.title);
                        } else {
                            titles.append(transStatusEnum.title + ",");
                        }
                    }
                }
            }
           return titles.toString();
        }
        return "未知状态";
    }
}
