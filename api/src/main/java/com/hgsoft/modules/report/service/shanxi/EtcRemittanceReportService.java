package com.hgsoft.modules.report.service.shanxi;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.shanxi.EtcRemittanceReport;

import javax.servlet.http.HttpServletResponse;

/**
 * 联网中心汇款报表service
 * Created by 吴鉴武 on 2021/5/6 16:08
 */
public interface EtcRemittanceReportService {

    /**
     * 分页查询汇款报表信息
     * @param page
     * @param etcRemittanceReport
     * @return
     */
    PageVo<EtcRemittanceReport> findPage(PageVo<EtcRemittanceReport> page,EtcRemittanceReport etcRemittanceReport);

    /**
     * 导出EXCEL
     * @param etcRemittanceReport
     */
    void exportExcel(HttpServletResponse response,EtcRemittanceReport etcRemittanceReport);
}
