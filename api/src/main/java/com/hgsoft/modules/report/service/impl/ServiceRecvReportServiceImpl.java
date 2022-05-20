package com.hgsoft.modules.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.ServiceRecvReport;
import com.hgsoft.modules.report.mapper.ServiceRecvReportMapper;
import com.hgsoft.modules.report.service.ServiceRecvReportService;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;

import com.hgsoft.ecip.web.util.SystemUtils;

import com.hgsoft.modules.utils.NewCallBackEcipEntityExcelView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.util.ExcelUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;

import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;



/**
 * 服务方应收报表ServiceImpl
 * @author 雷新辉
 * @version 2022-04-28 05:44:26
 */

@Slf4j
@RequiredArgsConstructor
@Service("com.hgsoft.modules.report.service.ServiceRecvReportService")
public class ServiceRecvReportServiceImpl extends CrudServiceImpl<ServiceRecvReportMapper, ServiceRecvReport> implements ServiceRecvReportService {

    /**
     * 分页查询
     * @param page
     * @param serviceRecvReport
     * @return
     */
    @Override
    public PageVo<ServiceRecvReport> serviceRecvReportPage(PageVo<ServiceRecvReport> page, ServiceRecvReport serviceRecvReport) {
        serviceRecvReport.setDataScope(SystemUtils.newInstance().findDataScope("serviceRecvReport:page"));
        page.initOrder();
        PageVo<ServiceRecvReport> iPage = (PageVo<ServiceRecvReport>) this.findPage(page, serviceRecvReport);
        if (CollUtil.isEmpty(iPage.getRecords())) return iPage;
        iPage.getRecords().replaceAll(vo -> {
            vo.setTotalSettlementAmt(vo.getTotalSettlementAmt().setScale(2,BigDecimal.ROUND_HALF_UP));
            vo.setRejectSettlementAmt(vo.getRejectSettlementAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setActualSettlementAmt(vo.getActualSettlementAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setBankTransferAmt(vo.getBankTransferAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            return vo;
        });
        ServiceRecvReport sum = this.baseMapper.getSum(serviceRecvReport);
        iPage.getDataMap().put("sum", sum);
        return iPage;
    }

    @Override
    public ServiceRecvReport getByPrimaryKey(String sysId) {
        return this.getById(sysId);
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, ServiceRecvReport serviceRecvReport) {
        List<ServiceRecvReport> list = this.findList(serviceRecvReport);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        if (CollUtil.isNotEmpty(list)) {
            ServiceRecvReport sum = this.baseMapper.getSum(serviceRecvReport);
            sum.setServiceRate("-");
            list.add(sum);

            BigDecimal hundred = new BigDecimal(100);
            list.replaceAll(vo -> {
                vo.setTotalSettlementAmt(vo.getTotalSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setRejectSettlementAmt(vo.getRejectSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setActualSettlementAmt(vo.getActualSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setBankTransferAmt(vo.getBankTransferAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setServiceAmt(vo.getServiceAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
                return vo;
            });
            //指定行数单元格横向合并
            int row = list.size() + 2;
            mv = new ModelAndView(new NewCallBackEcipEntityExcelView(workbook -> {
                Sheet sheet = workbook.getSheetAt(0);
                ExcelUtil.mergeRegion(sheet, row, row, 0, 4);
                sheet.getRow(row).getCell(0).setCellValue("合计");
                sheet.getRow(row).getCell(12).setCellValue("-");
            }));
        }
        mv.addObject(NormalExcelConstants.FILE_NAME, "服务方应收报表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, ServiceRecvReport.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("服务方应收报表", "服务方应收报表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

}