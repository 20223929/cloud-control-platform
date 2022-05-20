package com.hgsoft.modules.api.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.api.entity.AlarmCount;
import com.hgsoft.modules.api.entity.RecentCountVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.api.mapper.RecentCountMapper")
public interface RecentCountMapper extends DataMapper<RecentCountVo> {

    List<RecentCountVo> findByTime(RecentCountVo vo);
}
