package com.hgsoft.modules.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.modules.bankbillcheck.entity.TbEtcTransactionEexitExportExeclVo;
import com.hgsoft.modules.settlement.entity.SettlementSearchVo;
import com.hgsoft.modules.settlement.entity.TbCenterSettlement;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/**
 * 联网中心结算Service
 * @author 何志豪
 * @version 2021-05-08 04:55:35
 */
public interface TbCenterSettlementService {

    IPage<TbCenterSettlement> tbCenterSettlementPage(Page<TbCenterSettlement> page, SettlementSearchVo vo);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, SettlementSearchVo tbCenterSettlement);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    /**
     * 结算确认接口
     * @param e
     */
    void confirm(TbCenterSettlement e);

    IPage<TbEtcTransactionEexitExportExeclVo> findEexitPage(Page<TbEtcTransactionEexitExportExeclVo> page, SettlementSearchVo vo);

    ModelAndView exportTemplateForEexit(HttpServletRequest request);

    ModelAndView exportDataForEexit(HttpServletRequest request, SettlementSearchVo vo);

    Long countEexit(SettlementSearchVo vo);
}