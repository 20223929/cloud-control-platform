package com.hgsoft.modules.report.service.shanxi;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.shanxi.EtcPayReport;

import javax.servlet.http.HttpServletResponse;

/**
 * 联网中心扣款service
 * Created by 吴鉴武 on 2021/5/6 14:38
 */
public interface EtcPayReportService {

    /**
     * 分页查询联网中心扣款报表
     * @param page
     * @param etcPayReport
     * @return
     */
    PageVo<EtcPayReport> findPage(PageVo<EtcPayReport> page,EtcPayReport etcPayReport);

    /**
     * 导出EXCEL
     * @param etcPayReport
     */
    void exportExcel(HttpServletResponse response,EtcPayReport etcPayReport);
}
