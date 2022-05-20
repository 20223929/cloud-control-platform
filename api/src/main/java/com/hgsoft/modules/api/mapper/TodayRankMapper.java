package com.hgsoft.modules.api.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.api.entity.TodayRankVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.api.mapper.TodayRankMapper")
public interface TodayRankMapper extends DataMapper<TodayRankVo> {

    List<TodayRankVo> findByTime(TodayRankVo entity);
}
