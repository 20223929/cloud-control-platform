package com.hgsoft.modules.report.service;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.SettlementReport;
import com.hgsoft.ecip.framework.core.service.CrudService;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * 资金结算报表Service
 * @author 郑裕强
 * @version 2022-05-04 23:25:53
 */
public interface SettlementReportService extends CrudService<SettlementReport> {

    PageVo<SettlementReport> settlementReportPage(PageVo<SettlementReport> page, SettlementReport settlementReport);

    int updateBankTransferSta(String sysId, Integer bankTransferSta);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, SettlementReport settlementReport);
}