package com.hgsoft.modules.api.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.api.entity.AlarmCount;
import com.hgsoft.modules.api.entity.AlarmCountVo;

public interface AlarmCountService extends CrudService<AlarmCountVo> {
    /**
     * 异常查询和统计
     * @return
     */
    AlarmCount findAlarmCount();
}
