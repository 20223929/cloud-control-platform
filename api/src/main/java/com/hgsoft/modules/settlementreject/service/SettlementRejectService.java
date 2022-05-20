package com.hgsoft.modules.settlementreject.service;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.settlementreject.entity.SettlementReject;
import com.hgsoft.ecip.framework.core.service.CrudService;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * 退费详情表Service
 * @author 郑裕强
 * @version 2022-05-08 03:40:27
 */
public interface SettlementRejectService extends CrudService<SettlementReject> {

    PageVo<SettlementReject> settlementRejectPage(PageVo<SettlementReject> page, SettlementReject settlementReject);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, SettlementReject settlementReject);

}