package com.hgsoft.modules.report.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.report.entity.ClearAccountReport;
import com.hgsoft.modules.report.entity.PageVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 省中心清分对账报表Service
 * @author 吴鉴武
 * @date 2021-04-19 23:29:04
 */
public interface ClearAccountReportService extends CrudService<ClearAccountReport> {

    PageVo<ClearAccountReport> clearAccountReportPage(PageVo<ClearAccountReport> page, ClearAccountReport clearAccountReport);

    /**
     * 导出数据
     */
    void exportData(HttpServletRequest request,HttpServletResponse response, ClearAccountReport clearAccountReport);
}