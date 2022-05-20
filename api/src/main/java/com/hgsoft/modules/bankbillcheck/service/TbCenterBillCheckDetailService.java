package com.hgsoft.modules.bankbillcheck.service;

import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckDetail;

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
 * 联网中心对账核对明细表Service
 * @author 何志豪
 * @version 2021-04-21 02:34:34
 */
public interface TbCenterBillCheckDetailService extends CrudService<TbCenterBillCheckDetail> {

    IPage<TbCenterBillCheckDetail> tbCenterBillCheckDetailPage(Page<TbCenterBillCheckDetail> page, TbCenterBillCheckDetail tbCenterBillCheckDetail);

    TbCenterBillCheckDetail getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId, String equipmentId);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbCenterBillCheckDetail tbCenterBillCheckDetail);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);
}