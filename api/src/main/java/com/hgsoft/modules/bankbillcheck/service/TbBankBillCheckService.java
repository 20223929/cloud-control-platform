package com.hgsoft.modules.bankbillcheck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckSearchVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 银行对账核对表Service
 *
 * @author 何志豪
 * @version 2021-04-18 23:35:35
 */
public interface TbBankBillCheckService extends CrudService<TbBankBillCheck> {


    /**
     * 真正也上使用这个查询结果
     *
     * @param page
     * @param tbBankBillCheck
     * @return
     */
    IPage<TbBankBillCheck> findSearchDataPage(Page<TbBankBillCheck> page, TbBankBillCheckSearchVo tbBankBillCheck);

    TbBankBillCheck getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId);

    /**
     * @param tbBankBillCheck
     */
    void confirmCheckSumAndDetail(TbBankBillCheck tbBankBillCheck);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbBankBillCheckSearchVo tbBankBillCheck);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    /**
     * 用来计算需要导出的明细记录数有多少
     * @param checks
     * @return
     */
    Long countEexit(TbBankBillCheckSearchVo checks);

    /**
     * 导出流水明细
     * @param request
     * @param checks
     * @return
     */
    ModelAndView exportEexitDetailData(HttpServletRequest request,TbBankBillCheckSearchVo checks);

    /**
     * 导出流水明细文件的模板
     * @param request
     * @return
     */
    ModelAndView exportEexitTemplate(HttpServletRequest request);
}