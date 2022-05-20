package com.hgsoft.modules.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.ecip.auto.poi.excel.ExcelExportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.util.ExcelUtil;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.consts.Dimension;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.report.entity.ClearAccountReport;
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.mapper.ClearAccountReportMapper;
import com.hgsoft.modules.report.service.ClearAccountReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 省中心清分对账报表ServiceImpl
 * @author 吴鉴武
 * @date 2021-04-19 23:29:04
 */

@Service("com.hgsoft.modules.merchant.service.ClearAccountReportService")
@RequiredArgsConstructor
@Slf4j
public class ClearAccountReportServiceImpl extends CrudServiceImpl<ClearAccountReportMapper, ClearAccountReport> implements ClearAccountReportService {
    private final MerchantManageService merchantManageService;

    /**
     * 分页查询
     * @param page
     * @param clearAccountReport
     * @return
     */
    @Override
    public PageVo<ClearAccountReport> clearAccountReportPage(PageVo<ClearAccountReport> page, ClearAccountReport clearAccountReport) {
        page.initOrder();
        PageVo<ClearAccountReport> iPage = (PageVo<ClearAccountReport>) this.mapper.findPage(page, clearAccountReport, Dimension.getDimension(clearAccountReport.getValue()));
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(clearAccountReport.getUserMerchantParam() == null ? null : clearAccountReport.getUserMerchantParam().getSearchIds(),ShiroSecurityUtil.isSuperUser());
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        iPage.getRecords().replaceAll(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            return vo;
        });
        ClearAccountReport sum = this.baseMapper.getSum(clearAccountReport,Dimension.getDimension(clearAccountReport.getValue()));
        iPage.getDataMap().put("clearAccountReportSum",sum);
        return iPage;
    }

    /**
     * 导出数据
     */
    @Override
    public void exportData(HttpServletRequest request, HttpServletResponse response, ClearAccountReport clearAccountReport) {
        //查询数据
        List<ClearAccountReport> list = this.baseMapper.findListByCondition(clearAccountReport,Dimension.getDimension(clearAccountReport.getValue()));

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("省中心清分对账报表","省中心清分对账报表");

        //设置返回的下载文件名
        String codedFileName = "省中心清分对账报表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

        //设置响应头，返回下载流，下载格式为excel
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        //打开响应输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            log.error("打开响应输出流异常",e);
            return;
        }

        Workbook workbook = null;
        if(CollUtil.isEmpty(list)){
            try {
                workbook = ExcelExportUtil.exportExcel(params, MerchantAccountReport.class, list);
                if (workbook instanceof HSSFWorkbook) {
                    codedFileName = codedFileName + ".xls";
                } else {
                    codedFileName = codedFileName + ".xlsx";
                }
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(codedFileName, CharsetUtil.UTF_8));
                workbook.write(outputStream);
                outputStream.flush();
                return;
            }catch (Exception e){
                log.error("导出Excel文件异常",e);
                return;
            }finally {
                if(outputStream != null) IoUtil.close(outputStream);
                if(workbook != null) IoUtil.close(workbook);
            }
        }

        //根据数据创建文件并输出
        try {
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(clearAccountReport.getUserMerchantParam() == null ? null : clearAccountReport.getUserMerchantParam().getSearchIds(),ShiroSecurityUtil.isSuperUser());
            ClearAccountReport sum = list.stream().map(vo->{
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                return vo;
            }).reduce(new ClearAccountReport(),(oldValue,newValue)->{
                if(StrUtil.isNotBlank(oldValue.getMerchantGroupName())) {
                    oldValue.setClearCount(oldValue.getClearCount() + newValue.getClearCount());
                    oldValue.setClearFee(new BigDecimal(oldValue.getClearFee()).add(new BigDecimal(newValue.getClearFee())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                    oldValue.setDifferCount(oldValue.getDifferCount() + newValue.getDifferCount());
                    oldValue.setDifferFee(new BigDecimal(oldValue.getDifferFee()).add(new BigDecimal(newValue.getDifferFee())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                    oldValue.setExceptionCount(oldValue.getExceptionCount() + newValue.getExceptionCount());
                    oldValue.setExceptionFee(new BigDecimal(oldValue.getExceptionFee()).add(new BigDecimal(newValue.getExceptionFee())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                    oldValue.setSettlementCount(oldValue.getSettlementCount() + newValue.getSettlementCount());
                    oldValue.setSettlementMerchantTotalFee(new BigDecimal(oldValue.getSettlementMerchantTotalFee()).add(new BigDecimal(newValue.getSettlementMerchantTotalFee())).setScale(2).toString());
                    oldValue.setTotalCount(oldValue.getTotalCount() + newValue.getTotalCount());
                    oldValue.setTotalActualFee(new BigDecimal(oldValue.getTotalActualFee()).add(new BigDecimal(newValue.getTotalActualFee())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                    oldValue.setUnclearCount(oldValue.getUnclearCount() + newValue.getUnclearCount());
                    oldValue.setUnclearFee(new BigDecimal(oldValue.getUnclearFee()).add(new BigDecimal(newValue.getUnclearFee())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                }else{
                    oldValue.setMerchantGroupName("合计");
                    oldValue.setClearCount(newValue.getClearCount());
                    oldValue.setClearFee(newValue.getClearFee());
                    oldValue.setDifferCount(newValue.getDifferCount());
                    oldValue.setDifferFee(newValue.getDifferFee());
                    oldValue.setExceptionCount(newValue.getExceptionCount());
                    oldValue.setExceptionFee(newValue.getExceptionFee());
                    oldValue.setSettlementCount(newValue.getSettlementCount());
                    oldValue.setSettlementMerchantTotalFee(newValue.getSettlementMerchantTotalFee());
                    oldValue.setTotalCount(newValue.getTotalCount());
                    oldValue.setTotalActualFee(newValue.getTotalActualFee());
                    oldValue.setUnclearCount(newValue.getUnclearCount());
                    oldValue.setUnclearFee(newValue.getUnclearFee());
                }
                return oldValue;
            });
            list.add(sum);
            int row = list.size() + 1;
            workbook = ExcelExportUtil.exportExcel(params, ClearAccountReport.class, list);

            //align: 'center'
            Sheet sheet = workbook.getSheet(params.getSheetName());
            ExcelUtil.mergeRegion(sheet,row , row, 0, 3);

            try {
                if (workbook instanceof HSSFWorkbook) {
                    codedFileName = codedFileName + ".xls";
                } else {
                    codedFileName = codedFileName + ".xlsx";
                }
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(codedFileName,CharsetUtil.UTF_8));
                workbook.write(outputStream);
                outputStream.flush();
            } catch (Exception e) {
                log.error("导出Excel文件异常", e);
            }
        }finally {
            if(outputStream != null) IoUtil.close(outputStream);
            if(workbook != null) IoUtil.close(workbook);
        }
    }
}