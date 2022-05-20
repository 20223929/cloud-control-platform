package com.hgsoft.modules.report.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.SignStatisticReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 二次签约统计报表Service
 * @author 吴鉴武
 * @version 2021-04-20 22:01:36
 */
public interface SignStatisticReportService extends CrudService<SignStatisticReport> {

    PageVo<SignStatisticReport> signStatisticReportPage(PageVo<SignStatisticReport> page, SignStatisticReport signStatisticReport);
    /**
     * 导出数据
     */
    void exportData(HttpServletRequest request, HttpServletResponse response, SignStatisticReport signStatisticReport);
}