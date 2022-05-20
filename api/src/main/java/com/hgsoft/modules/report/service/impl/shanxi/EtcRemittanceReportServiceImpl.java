package com.hgsoft.modules.report.service.impl.shanxi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.ecip.auto.poi.excel.ExcelExportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.util.ExcelUtil;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.consts.Dimension;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.shanxi.EtcRemittanceReport;
import com.hgsoft.modules.report.mapper.shanxi.EtcRemittanceReportMapper;
import com.hgsoft.modules.report.service.shanxi.EtcRemittanceReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 吴鉴武 on 2021/5/6 16:09
 */
@Service
@Slf4j
public class EtcRemittanceReportServiceImpl implements EtcRemittanceReportService {

    @Autowired
    private EtcRemittanceReportMapper mapper;
    @Autowired
    private MerchantManageService merchantManageService;

    @Override
    public PageVo<EtcRemittanceReport> findPage(PageVo<EtcRemittanceReport> page, EtcRemittanceReport etcRemittanceReport) {
        PageVo<EtcRemittanceReport> iPage = this.mapper.findPage(page, etcRemittanceReport, Dimension.getDimension(etcRemittanceReport.getValue()));
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(etcRemittanceReport.getUserMerchantParam() == null ? null : etcRemittanceReport.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
        Map<String, List<EtcRemittanceReport>> groupList = iPage.getRecords().stream().map(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            return vo;
        }).collect(Collectors.groupingBy(key -> key.getMerchantGroupId() + "|" + key.getMerchantId() + "|" + key.getSiteId(), LinkedHashMap::new,Collectors.toList()));
        List<EtcRemittanceReport> result = new ArrayList<>(iPage.getRecords().size() + groupList.size());
        groupList.forEach((k,v)->{
            result.addAll(v);
            EtcRemittanceReport smallSum = v.stream().reduce(new EtcRemittanceReport(), (v1, v2) -> {
                if(StrUtil.isNotBlank(v1.getMerchantGroupName())){
                    v1.setRemittanceCount(v1.getRemittanceCount() + v2.getRemittanceCount());
                    v1.setRemittanceFee(v1.getRemittanceFee().add(v2.getRemittanceFee()));
                }else{
                    v1.setMerchantGroupName("小计");
                    v1.setRemittanceCount(v2.getRemittanceCount());
                    v1.setRemittanceFee(v2.getRemittanceFee());
                }
                return v1;
            });
            result.add(smallSum);
        });
        iPage.setRecords(result);
        EtcRemittanceReport sum = mapper.getSum(etcRemittanceReport);
        iPage.getDataMap().put("sum",sum);
        return iPage;
    }

    @Override
    public void exportExcel(HttpServletResponse response, EtcRemittanceReport etcRemittanceReport) {
        //查询数据
        List<EtcRemittanceReport> list = this.mapper.findListByCondition(etcRemittanceReport, Dimension.getDimension(etcRemittanceReport.getValue()));

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("联网中心汇款报表","联网中心汇款报表");

        //设置返回的下载文件名
        String codedFileName = "联网中心汇款报表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

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

        //根据数据创建文件并输出
        Workbook workbook = null;
        if(CollUtil.isEmpty(list)){
            try {
                workbook = ExcelExportUtil.exportExcel(params, EtcRemittanceReport.class, list);
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
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(etcRemittanceReport.getUserMerchantParam() == null ? null : etcRemittanceReport.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
            Map<String, List<EtcRemittanceReport>> groupList = list.stream().map(vo->{
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                return vo;
            }).collect(Collectors.groupingBy(key -> key.getMerchantGroupId() + "|" + key.getMerchantId() + "|" + key.getSiteId(), LinkedHashMap::new,Collectors.toList()));

            List<EtcRemittanceReport> result = new ArrayList<>(list.size() + groupList.size() + 1);
            List<Integer> indexList = new ArrayList<>(groupList.size() + 1);
            groupList.forEach((k,v)->{
                result.addAll(v);
                EtcRemittanceReport smallSum = v.stream().reduce(new EtcRemittanceReport(), (v1, v2) -> {
                    if(StrUtil.isNotBlank(v1.getMerchantGroupName())){
                        v1.setRemittanceCount(v1.getRemittanceCount() + v2.getRemittanceCount());
                        v1.setRemittanceFee(v1.getRemittanceFee().add(v2.getRemittanceFee()));
                    }else{
                        v1.setMerchantGroupName("小计");
                        v1.setRemittanceCount(v2.getRemittanceCount());
                        v1.setRemittanceFee(v2.getRemittanceFee());
                    }
                    return v1;
                });
                result.add(smallSum);
                indexList.add(result.size() + 1);
            });

            EtcRemittanceReport sum = result.stream().filter(vo -> "小计".equals(vo.getMerchantGroupName())).reduce(new EtcRemittanceReport(),(v1,v2)->{
                if(StrUtil.isNotBlank(v1.getMerchantGroupName())){
                    v1.setRemittanceCount(v1.getRemittanceCount() + v2.getRemittanceCount());
                    v1.setRemittanceFee(v1.getRemittanceFee().add(v2.getRemittanceFee()));
                }else{
                    v1.setMerchantGroupName("合计");
                    v1.setRemittanceCount(v2.getRemittanceCount());
                    v1.setRemittanceFee(v2.getRemittanceFee());
                }
                return v1;
            });
            result.add(sum);
            indexList.add(result.size() + 1);

            workbook = ExcelExportUtil.exportExcel(params, EtcRemittanceReport.class, result);

            //合并单元格
            Sheet sheet = workbook.getSheet(params.getSheetName());
            ListIterator<Integer> integerListIterator = indexList.listIterator();
            while (integerListIterator.hasNext()) {
                Integer next = integerListIterator.next();
                ExcelUtil.mergeRegion(sheet, next, next, 0, 3);
            }

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
