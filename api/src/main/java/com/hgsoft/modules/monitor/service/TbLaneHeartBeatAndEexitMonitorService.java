package com.hgsoft.modules.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.modules.monitor.entity.TbLaneHeartBeatAndEexitMonitor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface TbLaneHeartBeatAndEexitMonitorService {
    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbLaneHeartBeatAndEexitMonitor s);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    /**
     * 真正也上使用这个查询结果
     * @param page
     * @param entity
     * @return
     */
    IPage<TbLaneHeartBeatAndEexitMonitor> findSearchDataPage(Page<TbLaneHeartBeatAndEexitMonitor> page, TbLaneHeartBeatAndEexitMonitor entity);
}
