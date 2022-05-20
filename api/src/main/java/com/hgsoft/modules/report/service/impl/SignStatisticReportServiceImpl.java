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
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.SignStatisticReport;
import com.hgsoft.modules.report.mapper.SignStatisticReportMapper;
import com.hgsoft.modules.report.service.SignStatisticReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 二次签约统计报表ServiceImpl
 * @author 吴鉴武
 * @version 2021-04-20 22:01:36
 */

@Service("com.hgsoft.modules.report.service.SignStatisticReportService")
@Slf4j
public class SignStatisticReportServiceImpl extends CrudServiceImpl<SignStatisticReportMapper, SignStatisticReport> implements SignStatisticReportService {

    /**
     * 分页查询
     * @param page
     * @param signStatisticReport
     * @return
     */
    @Override
    public PageVo<SignStatisticReport> signStatisticReportPage(PageVo<SignStatisticReport> page, SignStatisticReport signStatisticReport) {
        page.initOrder();
        PageVo<SignStatisticReport> pageVo = (PageVo<SignStatisticReport>) this.findPage(page, signStatisticReport);
        if(CollUtil.isEmpty(pageVo.getRecords())) return pageVo;
        List<SignStatisticReport> list = this.mapper.findListByCondition(signStatisticReport);
        SignStatisticReport sum = list.stream().collect(Collectors.toMap(k->k.getSignInstitution() + "|" + k.getSignBank(),v->v,(old,newValue)->old.getStatisticsDate().compareTo(newValue.getStatisticsDate()) > 0 ? old : newValue))
                                      .values().stream().reduce(null,(v1,v2)->{
                                          if(v1 != null) {
                                              v1.setTotalCount(v1.getTotalCount() + v2.getTotalCount());
                                              v1.setNormalOunt(v1.getNormalOunt() + v2.getNormalOunt());
                                              v1.setIncreaseCount(v1.getIncreaseCount() + v2.getIncreaseCount());
                                              v1.setCancelCount(v1.getCancelCount() + v2.getCancelCount());
                                          }else{
                                              v1 = new SignStatisticReport();
                                              v1.setTotalCount(v2.getTotalCount());
                                              v1.setNormalOunt(v2.getNormalOunt());
                                              v1.setIncreaseCount(v2.getIncreaseCount());
                                              v1.setCancelCount(v2.getCancelCount());
                                          }
                                          return v1;
                                      });
        pageVo.getDataMap().put("sum",sum);
        return pageVo;
    }

    /**
     * 导出数据
     */
    @Override
    public void exportData(HttpServletRequest request, HttpServletResponse response, SignStatisticReport signStatisticReport) {
        //查询数据
        List<SignStatisticReport> list = this.baseMapper.findListByCondition(signStatisticReport);

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("二次签约统计报表","二次签约统计报表");

        //设置返回的下载文件名
        String codedFileName = "二次签约统计报表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

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
            SignStatisticReport sum = list.stream().collect(Collectors.toMap(k->k.getSignInstitution() + "|" + k.getSignBank(),v->v,(old,newValue)->old.getStatisticsDate().compareTo(newValue.getStatisticsDate()) > 1 ? old : newValue))
                    .values().stream().reduce(null,(v1,v2)->{
                        if(v1 != null) {
                            v1.setTotalCount(v1.getTotalCount() + v2.getTotalCount());
                            v1.setNormalOunt(v1.getNormalOunt() + v2.getNormalOunt());
                            v1.setIncreaseCount(v1.getIncreaseCount() + v2.getIncreaseCount());
                            v1.setCancelCount(v1.getCancelCount() + v2.getCancelCount());
                        }else{
                            v1 = new SignStatisticReport();
                            v1.setStatisticsDate("合计");
                            v1.setTotalCount(v2.getTotalCount());
                            v1.setNormalOunt(v2.getNormalOunt());
                            v1.setIncreaseCount(v2.getIncreaseCount());
                            v1.setCancelCount(v2.getCancelCount());
                        }
                        return v1;
                    });
            list.add(sum);
            int row = list.size() + 1;
            workbook = ExcelExportUtil.exportExcel(params, SignStatisticReport.class, list);

            //合并单元格
            Sheet sheet = workbook.getSheet(params.getSheetName());
            ExcelUtil.mergeRegion(sheet,row , row, 0, 2);

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