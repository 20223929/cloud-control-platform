package com.hgsoft.modules.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hgsoft.ecip.web.util.SystemUtils;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.SettlementReport;
import com.hgsoft.modules.report.mapper.SettlementReportMapper;
import com.hgsoft.modules.report.service.SettlementReportService;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.modules.utils.NewCallBackEcipEntityExcelView;
import org.apache.poi.ss.usermodel.Sheet;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;


/**
 * 资金结算报表ServiceImpl
 * @author 郑裕强
 * @version 2022-05-04 23:25:53
 */

@Service("com.hgsoft.modules.report.service.SettlementReportService")
public class SettlementReportServiceImpl extends CrudServiceImpl<SettlementReportMapper, SettlementReport> implements SettlementReportService {

    @Autowired
    private SettlementReportMapper settlementReportMapper;
    /**
     * 分页查询
     * @param page
     * @param settlementReport
     * @return
     */
    @Override
    public PageVo<SettlementReport> settlementReportPage(PageVo<SettlementReport> page, SettlementReport settlementReport) {
        settlementReport.setDataScope(SystemUtils.newInstance().findDataScope("settlementReport:page"));
        page.initOrder();
        PageVo<SettlementReport> iPage = (PageVo<SettlementReport>)this.findPage(page, settlementReport);
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        //数据修饰
        iPage.getRecords().replaceAll(vo -> {
            //数据修饰
            vo.setTotalSettlementAmt(vo.getTotalSettlementAmt().setScale(2,BigDecimal.ROUND_HALF_UP));
            vo.setRejectSettlementAmt(vo.getRejectSettlementAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setActualSettlementAmt(vo.getActualSettlementAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setBankTransferAmt(vo.getBankTransferAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setServiceRateTxt(vo.getServiceRate().multiply(new BigDecimal(1000)).doubleValue() + "‰");
            return vo;
        });
        //合计
        SettlementReport sum = this.mapper.getSum(settlementReport);
        iPage.getDataMap().put("sum", sum);
        return iPage;
    }

    @Override
    public int updateBankTransferSta(String sysId, Integer bankTransferSta) {
        return this.mapper.updateBankTransferSta(sysId, bankTransferSta);
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, SettlementReport settlementReport) {
        List<SettlementReport> list = this.findList(settlementReport);
        ModelAndView mv;
        if (CollUtil.isNotEmpty(list)) {
            BigDecimal hundred = BigDecimal.valueOf(100);
            //数据修饰
            list.replaceAll(vo -> {
                vo.setServiceRateTxt(vo.getServiceRate().multiply(new BigDecimal(1000)).doubleValue() + "‰");
                return vo;
            });
            SettlementReport sum = this.mapper.getSum(settlementReport);
            sum.setServiceSettlementIntervalTxt("-").setServiceRateTxt("-");
            list.add(sum);
            list.replaceAll(vo->{
                if(vo.getTotalSettlementAmt() != null) vo.setTotalSettlementAmt(vo.getTotalSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getRejectSettlementAmt() != null) vo.setRejectSettlementAmt(vo.getRejectSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getActualSettlementAmt() != null) vo.setActualSettlementAmt(vo.getActualSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getBankTransferAmt() != null) vo.setBankTransferAmt(vo.getBankTransferAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
                return vo;
            });
            //指定行数单元格横向合并
            int row = list.size() + 2;
            mv = new ModelAndView(new NewCallBackEcipEntityExcelView(workbook -> {
                Sheet sheet = workbook.getSheetAt(0);
                ExcelUtil.mergeRegion(sheet, row, row, 0, 4);
                sheet.getRow(row).getCell(0).setCellValue("合计");
            }));
        } else {
            mv = new ModelAndView(new JeecgEntityExcelView());
        }
        mv.addObject(NormalExcelConstants.FILE_NAME, "资金结算报表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, SettlementReport.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("资金结算报表", "资金结算报表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

}