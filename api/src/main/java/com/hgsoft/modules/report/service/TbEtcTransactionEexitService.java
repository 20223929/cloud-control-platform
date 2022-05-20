package com.hgsoft.modules.report.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import com.hgsoft.modules.report.entity.PageVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商户应收金额报表Service
 * @author 吴鉴武
 * @date 2021-04-15 03:08:01
 */
public interface TbEtcTransactionEexitService extends CrudService<MerchantAccountReport> {

    /**
     * 分页查询商户应收数据列表
     * @param page
     * @param queryParam
     * @return
     */
    PageVo<MerchantAccountReport> tbEtcTransactionEexitPage(PageVo<MerchantAccountReport> page, MerchantAccountReport queryParam);
    /**
     * 导出数据
     */
    void exportData(HttpServletRequest request, HttpServletResponse response, MerchantAccountReport queryParam);
}