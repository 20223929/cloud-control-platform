package com.hgsoft.modules.api.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.api.entity.AlarmCountVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.api.mapper.AlarmCountMapper")
public interface AlarmCountMapper extends DataMapper<AlarmCountVo> {
    /**
     * 异常数量
     * @return
     */
    List<AlarmCountVo> findAlarmCount();

    /**
     * 版本信息
     */
    AlarmCountVo findVersion(AlarmCountVo vo);
}
