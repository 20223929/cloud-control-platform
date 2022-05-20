package com.hgsoft.modules.monitor.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.modules.monitor.entity.TbLaneHeartBeatAndEexitMonitor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.monitor.mapper.TBLaneHeartBeatAndEexitMonitorMapper")
public interface TBLaneHeartBeatAndEexitMonitorMapper {
    /**
     * 用于分页查询
     *
     * @param page
     * @param entity
     * @return
     */
    public IPage<TbLaneHeartBeatAndEexitMonitor> findSearchDataPage(IPage<TbLaneHeartBeatAndEexitMonitor> page, @Param("entity") TbLaneHeartBeatAndEexitMonitor entity);

    /**
     * 用于导出
     *
     * @param entity
     * @return
     */
    public List<TbLaneHeartBeatAndEexitMonitor> findSearchDataAll(@Param("entity") TbLaneHeartBeatAndEexitMonitor entity);
}
