package com.hgsoft.modules.gdetc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.hgsoft.modules.enums.SettlementIntervalEnum;
import com.hgsoft.modules.gdetc.entity.GdetcProfit;
import com.hgsoft.modules.gdetc.entity.QueryCondition;
import com.hgsoft.modules.gdetc.mapper.GdetcProfitMapper;
import com.hgsoft.modules.gdetc.service.GdetcProfitService;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.utils.NewCallBackEcipEntityExcelView;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.util.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 联合电服收益service实现类
 * Created by 吴鉴武
 * date:2022/5/6 14:58
 */
@Service
@RequiredArgsConstructor
public class GdetcProfitServiceImpl implements GdetcProfitService {

    private final GdetcProfitMapper mapper;

    @Override
    public PageVo<GdetcProfit> findPage(PageVo<GdetcProfit> pageVo, QueryCondition condition) {
        PageVo<GdetcProfit> pageByCondition = mapper.findPageByCondition(pageVo, condition);
        if (CollUtil.isEmpty(pageByCondition.getRecords())) return pageByCondition;
        pageByCondition.getRecords().replaceAll(vo -> {
            //数据修饰
            vo.setCenterServiceRateFormat(vo.getCenterServiceRate().multiply(new BigDecimal(1000)).doubleValue() + "‰");
            vo.setTollSettlementIntervalFormat(SettlementIntervalEnum.getTitleByValue(vo.getTollSettlementInterval()));
            return vo;
        });
        GdetcProfit sum = mapper.findSum(condition);
        sum.setCenterServiceRateFormat("-");
        pageVo.getDataMap().put("sum", sum);
        return pageVo;
    }

    @Override
    public ModelAndView exportData(QueryCondition condition, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv;
        List<GdetcProfit> list = mapper.findListByCondition(condition);
        if (CollUtil.isNotEmpty(list)) {
            BigDecimal oneHundred = new BigDecimal(100);
            //非合计数据修饰
            list.replaceAll(vo -> {
                vo.setCenterServiceRateFormat(vo.getCenterServiceRate().multiply(new BigDecimal(1000)).doubleValue() + "‰");
                vo.setTollSettlementIntervalFormat(SettlementIntervalEnum.getTitleByValue(vo.getTollSettlementInterval()));
                return vo;
            });
            GdetcProfit sum = mapper.findSum(condition);
            sum.setCenterServiceRateFormat("-");
            list.add(sum);
            //所有数据包括合计金额修饰
            list.replaceAll(vo -> {
                vo.setActualGdetcAmt(vo.getActualGdetcAmt().divide(oneHundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setRejectGdetcAmt(vo.getRejectGdetcAmt().divide(oneHundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setTotalGdetcAmt(vo.getTotalGdetcAmt().divide(oneHundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setServiceGdetcAmt(vo.getServiceGdetcAmt().setScale(6, BigDecimal.ROUND_HALF_UP));
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
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "联合电服收益报表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        //注解对象Class
        mv.addObject(NormalExcelConstants.CLASS, GdetcProfit.class);
        //自定义表格参数
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联合电服收益报表", "联合电服收益"));
        //导出数据列表
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }
}
