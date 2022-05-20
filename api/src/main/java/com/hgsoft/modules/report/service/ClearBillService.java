package com.hgsoft.modules.report.service;

import com.hgsoft.modules.report.entity.ClearBill;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.report.entity.PageVo;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * 清分对账报表Service
 * @author 郑裕强
 * @version 2022-05-05 02:34:08
 */
public interface ClearBillService extends CrudService<ClearBill> {

    PageVo<ClearBill> clearBillPage(PageVo<ClearBill> page, ClearBill queryParam);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, ClearBill clearBill);

}