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
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.mapper.TbEtcTransactionEexitMapper;
import com.hgsoft.modules.report.service.TbEtcTransactionEexitService;
import com.hgsoft.modules.consts.Dimension;
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
import java.util.*;
import java.util.stream.Collectors;



/**
 * 商户应收金额报表ServiceImpl
 * @author 吴鉴武
 * @date 2021-04-15 03:08:01
 */

@Service("com.hgsoft.modules.demo.service.TbEtcTransactionEexitService")
@RequiredArgsConstructor
@Slf4j
public class TbEtcTransactionEexitServiceImpl extends CrudServiceImpl<TbEtcTransactionEexitMapper, MerchantAccountReport> implements TbEtcTransactionEexitService {
    private final MerchantManageService merchantManageService;


    /**
     * 分页查询
     * @param page
     * @param queryParam
     * @return
     */
    @Override
    public PageVo<MerchantAccountReport> tbEtcTransactionEexitPage(PageVo<MerchantAccountReport> page, MerchantAccountReport queryParam) {
        page.initOrder();
        PageVo<MerchantAccountReport> iPage = (PageVo<MerchantAccountReport>) this.mapper.findPage(page, queryParam, Dimension.getDimension(queryParam.getValue()));
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        ShiroUser principal = ShiroSecurityUtil.getPrincipal();
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(queryParam.getUserMerchantParam() == null ? null : queryParam.getUserMerchantParam().getSearchIds(),principal.getIsSuperUser());
        Map<String, List<MerchantAccountReport>> groupList = iPage.getRecords().stream().map(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            return vo;
        }).collect(Collectors.groupingBy(key -> key.getMerchantGroupId() + "|" + key.getMerchantId() + "|" + key.getSiteId(),LinkedHashMap::new,Collectors.toList()));
        List<MerchantAccountReport> result = new ArrayList<>(iPage.getRecords().size() + groupList.size());
        groupList.forEach((k,v)->{
            result.addAll(v);
            result.add(MerchantAccountReport.builder()
                    .merchantGroupName("小计")
                    .bankMerchantRefundTotalFee(v.stream().map(MerchantAccountReport::getBankMerchantRefundTotalFee).reduce(BigDecimal.ZERO,BigDecimal::add))
                    .bankMerchantTotalFee(v.stream().map(MerchantAccountReport::getBankMerchantTotalFee).reduce(BigDecimal.ZERO,BigDecimal::add))
                    .bankRefundCount(v.stream().mapToLong(MerchantAccountReport::getBankRefundCount).sum())
                    .bankTotalCount(v.stream().mapToLong(MerchantAccountReport::getBankTotalCount).sum())
                    .etcMerchantTotalFee(v.stream().map(MerchantAccountReport::getEtcMerchantTotalFee).reduce(BigDecimal.ZERO,BigDecimal::add))
                    .etcTotalCount(v.stream().mapToLong(MerchantAccountReport::getEtcTotalCount).sum())
                    .merchantTotalFee(v.stream().map(vo->vo.getMerchantTotalFee()).reduce(BigDecimal.ZERO,BigDecimal::add))
                    .settlementCount(v.stream().mapToLong(MerchantAccountReport::getSettlementCount).sum())
                    .settlementMerchantTotalFee(v.stream().map(MerchantAccountReport::getSettlementMerchantTotalFee).reduce(BigDecimal.ZERO,BigDecimal::add))
                    .totalCount(v.stream().mapToLong(MerchantAccountReport::getTotalCount).sum())
                    .unsettlementCount(v.stream().mapToLong(MerchantAccountReport::getUnsettlementCount).sum())
                    .unsettlementMerchantTotalFee(v.stream().map(MerchantAccountReport::getUnsettlementMerchantTotalFee).reduce(BigDecimal.ZERO,BigDecimal::add))
                    .build()
            );
        });
        iPage.setRecords(result);
        MerchantAccountReport merchantAccountSum = this.baseMapper.getSum(queryParam,Dimension.getDimension(queryParam.getValue()));
         iPage.getDataMap().put("merchantAccountSum",merchantAccountSum);
        return iPage;
    }

    /**
     * 导出数据
     */
    @Override
    public void exportData(HttpServletRequest request, HttpServletResponse response, MerchantAccountReport queryParam) {
        //查询数据
        List<MerchantAccountReport> list = this.baseMapper.findListByCondition(queryParam,Dimension.getDimension(queryParam.getValue()));

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("商户应收金额报表","商户应收金额报表");

        //设置返回的下载文件名
        String codedFileName = "商户应收金额报表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

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
                workbook = ExcelExportUtil.exportExcel(params, MerchantAccountReport.class, list);
                if (workbook instanceof HSSFWorkbook) {
                    codedFileName = codedFileName + ".xls";
                } else {
                    codedFileName = codedFileName + ".xlsx";
                }
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(codedFileName,CharsetUtil.UTF_8));
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
            ShiroUser principal = ShiroSecurityUtil.getPrincipal();
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(queryParam.getUserMerchantParam() == null ? null : queryParam.getUserMerchantParam().getSearchIds(),principal.getIsSuperUser());
            Map<String, List<MerchantAccountReport>> groupList = list.stream().map(vo -> {
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                return vo;
            }).collect(Collectors.groupingBy(key -> key.getMerchantGroupId() + "|" + key.getMerchantId() + "|" + key.getSiteId(), LinkedHashMap::new, Collectors.toList()));

            List<MerchantAccountReport> result = new ArrayList<>(list.size() + groupList.size() + 1);
            BigDecimal oneHundred = new BigDecimal(100);
            List<Integer> indexList = new ArrayList<>(groupList.size() + 1);

            groupList.forEach((k, v) -> {
                result.addAll(v);
                result.add(MerchantAccountReport.builder()
                        .merchantGroupName("小计")
                        .bankMerchantRefundTotalFee(v.stream().map(vo -> vo.getBankMerchantRefundTotalFee()).reduce(BigDecimal.ZERO, BigDecimal::add))
                        .bankMerchantTotalFee(v.stream().map(MerchantAccountReport::getBankMerchantTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add))
                        .bankRefundCount(v.stream().mapToLong(MerchantAccountReport::getBankRefundCount).sum())
                        .bankTotalCount(v.stream().mapToLong(MerchantAccountReport::getBankTotalCount).sum())
                        .etcMerchantTotalFee(v.stream().map(MerchantAccountReport::getEtcMerchantTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add))
                        .etcTotalCount(v.stream().mapToLong(MerchantAccountReport::getEtcTotalCount).sum())
                        .merchantTotalFee(v.stream().map(vo -> vo.getMerchantTotalFee()).reduce(BigDecimal.ZERO, BigDecimal::add))
                        .settlementCount(v.stream().mapToLong(MerchantAccountReport::getSettlementCount).sum())
                        .settlementMerchantTotalFee(v.stream().map(MerchantAccountReport::getSettlementMerchantTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add))
                        .totalCount(v.stream().mapToLong(MerchantAccountReport::getTotalCount).sum())
                        .unsettlementCount(v.stream().mapToLong(MerchantAccountReport::getUnsettlementCount).sum())
                        .unsettlementMerchantTotalFee(v.stream().map(MerchantAccountReport::getUnsettlementMerchantTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add))
                        .build()
                );
                indexList.add(result.size() + 1);
            });

            MerchantAccountReport merchantAccountReport = result.stream().filter(vo -> "小计".equals(vo.getMerchantGroupName())).reduce(new MerchantAccountReport(),(oldValue,newValue)->{
                if(StrUtil.isNotBlank(oldValue.getMerchantGroupName())) {
                    oldValue.setBankMerchantRefundTotalFee(oldValue.getBankMerchantRefundTotalFee().add(newValue.getBankMerchantRefundTotalFee()));
                    oldValue.setBankMerchantTotalFee(oldValue.getBankMerchantRefundTotalFee().add(newValue.getBankMerchantRefundTotalFee()));
                    oldValue.setBankRefundCount(oldValue.getBankRefundCount() + newValue.getBankRefundCount());
                    oldValue.setBankTotalCount(oldValue.getBankTotalCount() + newValue.getBankTotalCount());
                    oldValue.setEtcMerchantTotalFee(oldValue.getEtcMerchantTotalFee().add(newValue.getEtcMerchantTotalFee()));
                    oldValue.setEtcTotalCount(oldValue.getEtcTotalCount() + newValue.getEtcTotalCount());
                    oldValue.setMerchantTotalFee(oldValue.getMerchantTotalFee().add(newValue.getMerchantTotalFee()));
                    oldValue.setSettlementCount(oldValue.getSettlementCount() + newValue.getSettlementCount());
                    oldValue.setSettlementMerchantTotalFee(oldValue.getSettlementMerchantTotalFee().add(newValue.getSettlementMerchantTotalFee()));
                    oldValue.setTotalCount(oldValue.getTotalCount() + newValue.getTotalCount());
                    oldValue.setUnsettlementCount(oldValue.getUnsettlementCount() + newValue.getUnsettlementCount());
                    oldValue.setUnsettlementMerchantTotalFee(oldValue.getUnsettlementMerchantTotalFee().add(newValue.getUnsettlementMerchantTotalFee()));
                }else{
                    oldValue.setMerchantGroupName("合计");
                    oldValue.setBankMerchantRefundTotalFee(newValue.getBankMerchantRefundTotalFee());
                    oldValue.setBankMerchantTotalFee(newValue.getBankMerchantRefundTotalFee());
                    oldValue.setBankRefundCount(newValue.getBankRefundCount());
                    oldValue.setBankTotalCount(newValue.getBankTotalCount());
                    oldValue.setEtcMerchantTotalFee(newValue.getEtcMerchantTotalFee());
                    oldValue.setEtcTotalCount(newValue.getEtcTotalCount());
                    oldValue.setMerchantTotalFee(newValue.getMerchantTotalFee());
                    oldValue.setSettlementCount(newValue.getSettlementCount());
                    oldValue.setSettlementMerchantTotalFee(newValue.getSettlementMerchantTotalFee());
                    oldValue.setTotalCount(newValue.getTotalCount());
                    oldValue.setUnsettlementCount(newValue.getUnsettlementCount());
                    oldValue.setUnsettlementMerchantTotalFee(newValue.getUnsettlementMerchantTotalFee());
                }
                return oldValue;
            });
            result.add(merchantAccountReport);
            indexList.add(result.size() + 1);

            workbook = ExcelExportUtil.exportExcel(params, MerchantAccountReport.class, result);

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