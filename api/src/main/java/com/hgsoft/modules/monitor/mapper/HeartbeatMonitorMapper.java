package com.hgsoft.modules.monitor.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.monitor.entity.HeartbeatMonitor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车道监控MAPPER接口
 * @author 吴鉴武
 * @version 2021-06-01 22:47:13
 */
@Repository("com.hgsoft.modules.monitor.mapper.HeartbeatMonitorMapper")
public interface HeartbeatMonitorMapper extends DataMapper<HeartbeatMonitor> {

    @Override
    List<HeartbeatMonitor> findList(@Param("entity") HeartbeatMonitor heartbeatMonitor);
}