package com.hgsoft.modules.bankbillcheck.service;

import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckDetail;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.ecip.framework.common.response.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 银行对账明细表Service
 * @author 何志豪
 * @version 2021-04-19 03:07:10
 */
public interface TbBankBillCheckDetailService extends CrudService<TbBankBillCheckDetail> {

    IPage<TbBankBillCheckDetail> tbBankBillCheckDetailPage(Page<TbBankBillCheckDetail> page, TbBankBillCheckDetail tbBankBillCheckDetail);

    TbBankBillCheckDetail getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId, String equipmentId);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbBankBillCheckDetail tbBankBillCheckDetail);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);
}