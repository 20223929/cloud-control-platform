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
import com.hgsoft.modules.report.entity.shanxi.EtcPayReport;
import com.hgsoft.modules.report.mapper.shanxi.EtcPayReportMapper;
import com.hgsoft.modules.report.service.shanxi.EtcPayReportService;
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
 * 联网中心扣款serviceImpl
 * Created by 吴鉴武 on 2021/5/6 14:39
 */
@Service
@Slf4j
public class EtcPayReportServiceImpl implements EtcPayReportService{

    @Autowired
    private EtcPayReportMapper mapper;
    @Autowired
    private MerchantManageService merchantManageService;

    @Override
    public PageVo<EtcPayReport> findPage(PageVo<EtcPayReport> page, EtcPayReport etcPayReport) {
        PageVo<EtcPayReport> iPage = mapper.findPage(page, etcPayReport, Dimension.getDimension(etcPayReport.getValue()));
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(etcPayReport.getUserMerchantParam() == null ? null : etcPayReport.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
        Map<String, List<EtcPayReport>> groupList = iPage.getRecords().stream().map(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            return vo;
        }).collect(Collectors.groupingBy(key -> key.getMerchantGroupId() + "|" + key.getMerchantId() + "|" + key.getSiteId(), LinkedHashMap::new,Collectors.toList()));
        List<EtcPayReport> result = new ArrayList<>(iPage.getRecords().size() + groupList.size());
        groupList.forEach((k,v)->{
            result.addAll(v);
            EtcPayReport smallSum = v.stream().reduce(new EtcPayReport(), (v1, v2) -> {
                if(StrUtil.isNotBlank(v1.getMerchantGroupName())){
                    v1.setTotalCount(v1.getTotalCount() + v2.getTotalCount());
                    v1.setTotalFee(v1.getTotalFee().add(v2.getTotalFee()));
                    v1.setPayCount(v1.getPayCount() + v2.getPayCount());
                    v1.setPayFee(v1.getPayFee().add(v2.getPayFee()));
                    v1.setUnpayCount(v1.getUnpayCount() + v2.getUnpayCount());
                    v1.setUnpayFee(v1.getUnpayFee().add(v2.getUnpayFee()));
                }else{
                    v1.setMerchantGroupName("小计");
                    v1.setTotalCount(v2.getTotalCount());
                    v1.setTotalFee(v2.getTotalFee());
                    v1.setPayCount(v2.getPayCount());
                    v1.setPayFee(v2.getPayFee());
                    v1.setUnpayCount(v2.getUnpayCount());
                    v1.setUnpayFee(v2.getUnpayFee());
                }
                return v1;
            });
            result.add(smallSum);
        });
        iPage.setRecords(result);
        EtcPayReport sum = mapper.getSum(etcPayReport);
        iPage.getDataMap().put("sum",sum);
        return iPage;
    }

    @Override
    public void exportExcel(HttpServletResponse response, EtcPayReport etcPayReport) {
        //查询数据
        List<EtcPayReport> list = this.mapper.findListByCondition(etcPayReport, Dimension.getDimension(etcPayReport.getValue()));

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("联网中心扣款实时报表","联网中心扣款实时报表");

        //设置返回的下载文件名
        String codedFileName = "联网中心扣款实时报表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

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
                workbook = ExcelExportUtil.exportExcel(params, EtcPayReport.class, list);
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
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(etcPayReport.getUserMerchantParam() == null ? null : etcPayReport.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
            Map<String, List<EtcPayReport>> groupList = list.stream().map(vo->{
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                return vo;
            }).collect(Collectors.groupingBy(key -> key.getMerchantGroupId() + "|" + key.getMerchantId() + "|" + key.getSiteId(), LinkedHashMap::new,Collectors.toList()));

            List<EtcPayReport> result = new ArrayList<>(list.size() + groupList.size() + 1);
            List<Integer> indexList = new ArrayList<>(groupList.size() + 1);
            groupList.forEach((k,v)->{
                result.addAll(v);
                EtcPayReport smallSum = v.stream().reduce(new EtcPayReport(), (v1, v2) -> {
                    if(StrUtil.isNotBlank(v1.getMerchantGroupName())){
                        v1.setTotalCount(v1.getTotalCount() + v2.getTotalCount());
                        v1.setTotalFee(v1.getTotalFee().add(v2.getTotalFee()));
                        v1.setPayCount(v1.getPayCount() + v2.getPayCount());
                        v1.setPayFee(v1.getPayFee().add(v2.getPayFee()));
                        v1.setUnpayCount(v1.getUnpayCount() + v2.getUnpayCount());
                        v1.setUnpayFee(v1.getUnpayFee().add(v2.getUnpayFee()));
                    }else{
                        v1.setMerchantGroupName("小计");
                        v1.setTotalCount(v2.getTotalCount());
                        v1.setTotalFee(v2.getTotalFee());
                        v1.setPayCount(v2.getPayCount());
                        v1.setPayFee(v2.getPayFee());
                        v1.setUnpayCount(v2.getUnpayCount());
                        v1.setUnpayFee(v2.getUnpayFee());
                    }
                    return v1;
                });
                result.add(smallSum);
                indexList.add(result.size() + 1);
            });

            EtcPayReport sum = result.stream().filter(vo -> "小计".equals(vo.getMerchantGroupName())).reduce(new EtcPayReport(),(v1,v2)->{
                if(StrUtil.isNotBlank(v1.getMerchantGroupName())){
                    v1.setTotalCount(v1.getTotalCount() + v2.getTotalCount());
                    v1.setTotalFee(v1.getTotalFee().add(v2.getTotalFee()));
                    v1.setPayCount(v1.getPayCount() + v2.getPayCount());
                    v1.setPayFee(v1.getPayFee().add(v2.getPayFee()));
                    v1.setUnpayCount(v1.getUnpayCount() + v2.getUnpayCount());
                    v1.setUnpayFee(v1.getUnpayFee().add(v2.getUnpayFee()));
                }else{
                    v1.setMerchantGroupName("合计");
                    v1.setTotalCount(v2.getTotalCount());
                    v1.setTotalFee(v2.getTotalFee());
                    v1.setPayCount(v2.getPayCount());
                    v1.setPayFee(v2.getPayFee());
                    v1.setUnpayCount(v2.getUnpayCount());
                    v1.setUnpayFee(v2.getUnpayFee());
                }
                return v1;
            });
            result.add(sum);
            indexList.add(result.size() + 1);

            workbook = ExcelExportUtil.exportExcel(params, EtcPayReport.class, result);

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
