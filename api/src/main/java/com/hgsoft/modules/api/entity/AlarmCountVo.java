package com.hgsoft.modules.api.entity;

import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "异常查询和统计", description = "异常查询和统计")
public class AlarmCountVo extends DataEntity<AlarmCountVo> implements Serializable {
    private static final long serialVersionUID = 5452156346875458795L;
    /**
     * 报警数量
     */
    private Integer count;
    /**
     * 异常级别：1-一般 2-严重
     */
    private Integer alarmLevel;
    /**
     * 黑名单版本
     */
    private String version;
    /**
     * 黑名单类型：1-ETC卡黑名单 2-OBU黑名单
     */
    private Integer blackType;
    /**
     * 包类型：0-全量 1-增量
     */
    private Integer packageType;

}
