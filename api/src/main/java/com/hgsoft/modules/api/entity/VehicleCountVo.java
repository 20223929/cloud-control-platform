package com.hgsoft.modules.api.entity;

import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "过车交易统计", description = "过车交易统计")
public class VehicleCountVo extends DataEntity<VehicleCountVo> implements Serializable {
    private static final long serialVersionUID = -6356789069537992721L;

    private Integer serviceType;
    private Long total;
    private Long fee;

    private Date beginTime;
    private Date endTime;

}
