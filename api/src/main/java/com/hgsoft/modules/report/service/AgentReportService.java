package com.hgsoft.modules.report.service;

import com.hgsoft.modules.report.entity.AgentReport;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.ecip.framework.common.response.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.ServiceRecvReport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 代理商收益报表Service
 * @author
 * @version 2022-05-06 23:07:31
 */
public interface AgentReportService extends CrudService<AgentReport> {

    PageVo<AgentReport> agentReportPage(PageVo<AgentReport> page, AgentReport agentReport);

    AgentReport getByPrimaryKey(String sysId);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, AgentReport agentReport);

}