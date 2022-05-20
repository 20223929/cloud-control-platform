package com.hgsoft.modules.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hgsoft.modules.consts.Dimension;
import com.hgsoft.modules.report.entity.ClearBill;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.mapper.ClearBillMapper;
import com.hgsoft.modules.report.service.ClearBillService;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.web.util.SystemUtils;
import com.hgsoft.modules.utils.NewCallBackEcipEntityExcelView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.util.ExcelUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;



/**
 * 清分对账报表ServiceImpl
 * @author 郑裕强
 * @version 2022-05-05 02:34:08
 */

@Service("com.hgsoft.modules.report.service.ClearBillService")
@RequiredArgsConstructor
@Slf4j
public class ClearBillServiceImpl extends CrudServiceImpl<ClearBillMapper, ClearBill> implements ClearBillService {

    /**
     * 分页查询
     * @param page
     * @param queryParam
     * @return
     */
    @Override
    public PageVo<ClearBill> clearBillPage(PageVo<ClearBill> page, ClearBill queryParam) {
        queryParam.setDataScope(SystemUtils.newInstance().findDataScope("clearBill:page"));
        page.initOrder();
        PageVo<ClearBill> iPage = (PageVo<ClearBill>) this.mapper.clearBillPage(page, queryParam, Dimension.getDimension(queryParam.getValue()));
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        ClearBill sum = this.baseMapper.getSum(queryParam,Dimension.getDimension(queryParam.getValue()));
        iPage.getDataMap().put("sum",sum);
        return iPage;
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, ClearBill clearBill) {
        List<ClearBill> list = this.mapper.findList(clearBill, Dimension.getDimension(clearBill.getValue()));
        ModelAndView mv;
        if (CollUtil.isNotEmpty(list)) {
            BigDecimal hundred = BigDecimal.valueOf(100);
            ClearBill sum = this.mapper.getSum(clearBill, Dimension.getDimension(clearBill.getValue()));
            sum.setTollSettlementIntervalTxt("-");
            list.add(sum);
            list.replaceAll(vo->{
                if(vo.getTotalTransAmt() != null) vo.setTotalTransAmt(vo.getTotalTransAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getTotalSettlementAmt() != null) vo.setTotalSettlementAmt(vo.getTotalSettlementAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getTotalClearAmt() != null) vo.setTotalClearAmt(vo.getTotalClearAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getTotalUnclearAmt() != null) vo.setTotalUnclearAmt(vo.getTotalUnclearAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getClearExceptionAmt() != null) vo.setClearExceptionAmt(vo.getClearExceptionAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getClearBadAmt() != null) vo.setClearBadAmt(vo.getClearBadAmt().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                return vo;
            });
            //指定行数单元格横向合并
            int row = list.size() + 2;
            mv = new ModelAndView(new NewCallBackEcipEntityExcelView(workbook -> {
                Sheet sheet = workbook.getSheetAt(0);
                ExcelUtil.mergeRegion(sheet, row, row, 0, 2);
                sheet.getRow(row).getCell(0).setCellValue("合计");
            }));
        } else {
            mv = new ModelAndView(new JeecgEntityExcelView());
        }
        mv.addObject(NormalExcelConstants.FILE_NAME, "清分对账报表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, ClearBill.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("清分对账报表", "清分对账报表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

}