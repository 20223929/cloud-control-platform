package com.hgsoft.modules.api.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.api.entity.RecentCount;
import com.hgsoft.modules.api.entity.RecentCountVo;


public interface RecentCountService extends CrudService<RecentCountVo> {

    RecentCount findByTime();
}
