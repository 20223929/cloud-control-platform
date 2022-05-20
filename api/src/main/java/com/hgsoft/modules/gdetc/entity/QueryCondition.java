package com.hgsoft.modules.gdetc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件实体类
 * Created by 吴鉴武
 * date:2022/5/6 10:30
 */
@Data
public class QueryCondition implements Serializable {
    private static final long serialVersionUID = 8534619570230164001L;

    private Integer serviceType;
    private String merchantGroupId;
    private String merchantId;
    private String siteId;
    private String[] transTime;
    private Integer interval;
}
