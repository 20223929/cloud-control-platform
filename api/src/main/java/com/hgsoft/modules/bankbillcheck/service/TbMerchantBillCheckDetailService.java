package com.hgsoft.modules.bankbillcheck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetailExportExcelVo;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetailSearchVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface TbMerchantBillCheckDetailService {
    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbMerchantBillCheckDetailSearchVo s);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    /**
     * 真正也上使用这个查询结果
     * @param page
     * @param entity
     * @return
     */
    IPage<TbMerchantBillCheckDetailExportExcelVo> findSearchDataPage(Page<TbMerchantBillCheckDetail> page,
                                                                     TbMerchantBillCheckDetailSearchVo entity);
}
