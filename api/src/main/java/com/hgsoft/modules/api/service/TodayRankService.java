package com.hgsoft.modules.api.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.api.entity.TodayRank;
import com.hgsoft.modules.api.entity.TodayRankVo;

public interface TodayRankService extends CrudService<TodayRankVo> {
    /**
     * 今日交易额排行查询
     * @return
     */
    TodayRank findTodayRank(TodayRankVo vo);
}
