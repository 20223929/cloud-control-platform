package com.hgsoft.modules.bankbillcheck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtc;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtcExportExcelVo;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtcSearchVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 商户对账服务类
 */
public interface TBMerchantBillCheckExpPlatformEtcService {

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TBMerchantBillCheckExpPlatformEtcSearchVo s);

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
    IPage<TBMerchantBillCheckExpPlatformEtcExportExcelVo> findSearchDataPage(Page<TBMerchantBillCheckExpPlatformEtc> page,
                                                TBMerchantBillCheckExpPlatformEtcSearchVo entity);

    /**
     * 异常登记
     * 1.成功会返回成功
     * 2.异常会返回具体异常信息
     * @param param
     * @return
     */
    String errorRegister(TBMerchantBillCheckExpPlatformEtc param);

    /**
     * 确认登记
     * 1.成功会返回成功
     * 2.异常会返回具体异常信息
     * @param param
     * @return
     */
    String confirmRegister(TBMerchantBillCheckExpPlatformEtc param);

}
