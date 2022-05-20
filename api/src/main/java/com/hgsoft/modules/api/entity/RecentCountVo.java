package com.hgsoft.modules.api.entity;

import com.hgsoft.ecip.framework.core.entity.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RecentCountVo extends DataEntity<RecentCountVo> implements Serializable {
    private static final long serialVersionUID = 3797992061238292306L;

    private String month;
    private Long count;
    private Long amount;

    private Date beginTime;
    private Date endTime;

}
