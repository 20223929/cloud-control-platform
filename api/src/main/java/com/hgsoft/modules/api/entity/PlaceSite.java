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
@ApiModel(value = "拓展场所列表查询", description = "拓展场所列表查询")
public class PlaceSite implements Serializable {
    private static final long serialVersionUID = 9074252127456922942L;

    /**
     * 拓展场所服务类型 2-停车场、3-加油站
     */
    private Integer service_type;
    /**
     * 拓展场所名称
     */
    private String name;
    /**
     * 经度
     */
    private String lng;
    /**
     * 维度
     */
    private String lat;
}
