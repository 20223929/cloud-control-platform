package com.hgsoft.modules.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.monitor.entity.HeartbeatMonitor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
/**
 * 车道监控Service
 * @author 吴鉴武
 * @version 2021-06-01 22:47:13
 */
public interface HeartbeatMonitorService extends CrudService<HeartbeatMonitor> {

    /**
     * 分页查询车道监控数据
     * @param page
     * @param heartbeatMonitor
     * @return
     */
    IPage<HeartbeatMonitor> heartbeatMonitorPage(Page<HeartbeatMonitor> page, HeartbeatMonitor heartbeatMonitor);

    /**
     * 导出车道监控数据
     */
    ModelAndView exportData(HttpServletResponse response, HeartbeatMonitor heartbeatMonitor);
}