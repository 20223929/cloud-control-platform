package com.hgsoft.modules.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.merchant.entity.Site;
import com.hgsoft.modules.merchant.mapper.SiteMapper;
import com.hgsoft.modules.report.entity.AgentReport;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.mapper.AgentReportMapper;
import com.hgsoft.modules.report.service.AgentReportService;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;

import com.hgsoft.ecip.web.util.SystemUtils;

import com.hgsoft.modules.utils.NewCallBackEcipEntityExcelView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;


import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;



/**
 * 代理商收益报表ServiceImpl
 * @author
 * @version 2022-05-06 23:07:31
 */
@Slf4j
@RequiredArgsConstructor
@Service("com.hgsoft.modules.report.service.AgentReportService")
public class AgentReportServiceImpl extends CrudServiceImpl<AgentReportMapper, AgentReport> implements AgentReportService {
    @Autowired
    private SiteMapper siteMapper;

    /**
     * 分页查询
     * @param page
     * @param agentReport
     * @return
     */
    @Override
    public PageVo<AgentReport> agentReportPage(PageVo<AgentReport> page, AgentReport agentReport) {
        String userId = ShiroSecurityUtil.account();
        List<String> siteIds = null;
        if (!userId.equals("admin")) {
            siteIds = siteMapper.findSiteIdByAgentId(userId);
        }
        agentReport.setDataScope(SystemUtils.newInstance().findDataScope("agentReport:page"));
        PageVo<AgentReport> iPage = this.baseMapper.findPageByCondition(page, agentReport, siteIds);
        if (CollUtil.isEmpty(iPage.getRecords())) return iPage;
        iPage.getRecords().replaceAll(vo -> {
            vo.setTotalSettlementAmt(vo.getTotalSettlementAmt().setScale(2,BigDecimal.ROUND_HALF_UP));
            vo.setRejectSettlementAmt(vo.getRejectSettlementAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setActualSettlementAmt(vo.getActualSettlementAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setServiceAmt(vo.getServiceAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
            vo.setAgentAmt(vo.getAgentAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
            vo.setCenterServiceAmt(vo.getCenterServiceAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
            return vo;
        });
        AgentReport sum = this.baseMapper.getSum(agentReport, siteIds);
        iPage.getDataMap().put("sum", sum);
        return iPage;
    }

    @Override
    public AgentReport getByPrimaryKey(String sysId) {
        return this.getById(sysId);
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, AgentReport agentReport) {
        String userId = ShiroSecurityUtil.account();
        List<String> siteIds = null;
        if (!userId.equals("admin")) {
            siteIds = siteMapper.findSiteIdByAgentId(userId);
        }
        List<AgentReport> list = this.baseMapper.findListByCondition(agentReport, siteIds);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        if (CollUtil.isNotEmpty(list)) {
            AgentReport sum  =  this.baseMapper.getSum(agentReport, siteIds);
            sum.setServiceRate("-").setAgentRate("-");
            list.add(sum);

            BigDecimal hundred = new BigDecimal(100);
            list.replaceAll(vo -> {
                vo.setTotalSettlementAmt(vo.getTotalSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setRejectSettlementAmt(vo.getRejectSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setActualSettlementAmt(vo.getActualSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setServiceAmt(vo.getServiceAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
                vo.setAgentAmt(vo.getAgentAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
                vo.setCenterServiceAmt(vo.getCenterServiceAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
                return vo;
            });
            //指定行数单元格横向合并
            int row = list.size() + 2;
            mv = new ModelAndView(new NewCallBackEcipEntityExcelView(workbook -> {
                Sheet sheet = workbook.getSheetAt(0);
                ExcelUtil.mergeRegion(sheet, row, row, 0, 4);
                sheet.getRow(row).getCell(0).setCellValue("合计");
            }));
        }
        mv.addObject(NormalExcelConstants.FILE_NAME, "代理商收益报表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, AgentReport.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("代理商收益报表", "代理商收益报表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }
}