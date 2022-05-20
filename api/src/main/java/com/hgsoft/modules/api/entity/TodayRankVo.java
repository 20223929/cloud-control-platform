package com.hgsoft.modules.api.entity;

import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.integration.annotation.Default;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "今日交易额排行查询", description = "今日交易额排行查询")
public class TodayRankVo extends DataEntity<TodayRankVo> implements Serializable {
    private static final long serialVersionUID = -6805147140421218249L;

    /**
     * 2-停车场、3-加油站 4-服务区 5-市政拓展 6-充电桩
     */
    private Integer serviceType;
    private String siteId;
    private String name = "未知场所";
    private Long fee;
    private Date beginTime;
    private Date endTime;

}
