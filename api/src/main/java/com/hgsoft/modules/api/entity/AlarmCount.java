package com.hgsoft.modules.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "异常查询和统计", description = "异常查询和统计")
public class AlarmCount implements Serializable {
    private static final long serialVersionUID = 5395818635778495591L;

    /**
     * 严重报警数量
     */
    private Integer serious_alarm_count;
    /**
     * 一般报警数量
     */
    private Integer general_alarm_count;
    /**
     * 全量ETC卡黑名单版本
     */
    private String full_card_blacklist_version;
    /**
     * 增量ETC卡黑名单版本
     */
    private String incre_card_blacklist_version;
    /**
     * 全量OBU黑名单版本
     */
    private String full_obu_blacklist_version;
    /**
     * 增量OBU黑名单版本
     */
    private String incre_obu_blacklist_version;
}
