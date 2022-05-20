package com.hgsoft.modules.settlement.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.settlement.entity.TbBankSettlement;
import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;

import javax.servlet.http.HttpServletResponse;
/**
 * 银行预结算Service
 * @author 何志豪
 * @version 2021-05-08 05:23:48
 */
public interface TbBankSettlementService extends CrudService<TbBankSettlement> {

    /**
     * 分页查询银行预结算信息
     * @param page
     * @param tbBankSettlement
     * @return
     */
    PageVo<TbBankSettlement> tbBankSettlementPage(PageVo<TbBankSettlement> page, TbBankSettlement tbBankSettlement);

    /**
     * 导出数据
     */
    void exportData(HttpServletResponse response, TbBankSettlement tbBankSettlement);

    /**
     * 导出明细数据
     * @param response
     * @param detail
     */
    void exportDetailData(HttpServletResponse response,TbBankSettlementDetail detail);

    /**
     * 查询明细
     * @param page
     * @param detail
     * @return
     */
    PageVo<TbBankSettlementDetail> detail(PageVo<TbBankSettlementDetail> page, TbBankSettlementDetail detail);
}