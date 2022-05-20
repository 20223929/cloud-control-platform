package com.hgsoft.modules.settlement.service.impl;

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
import com.hgsoft.modules.merchant.enums.ServiceTypeEnum;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.merchantcommon.NodeLevelEnum;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.settlement.entity.TbBankSettlement;
import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;
import com.hgsoft.modules.settlement.enums.TransactionType;
import com.hgsoft.modules.settlement.mapper.TbBankSettlementMapper;
import com.hgsoft.modules.settlement.service.TbBankSettlementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 银行预结算ServiceImpl
 * @author 何志豪
 * @version 2021-05-08 05:23:48
 */

@Service("com.hgsoft.modules.settlement.service.TbBankSettlementService")
@Slf4j
public class TbBankSettlementServiceImpl extends CrudServiceImpl<TbBankSettlementMapper, TbBankSettlement> implements TbBankSettlementService {

    @Autowired
    private MerchantManageService merchantManageService;

    /**
     * 分页查询
     * @param page
     * @param tbBankSettlement
     * @return
     */
    @Override
    public PageVo<TbBankSettlement> tbBankSettlementPage(PageVo<TbBankSettlement> page, TbBankSettlement tbBankSettlement) {
        PageVo<TbBankSettlement> page1 = this.mapper.findPage(page, tbBankSettlement);
        if(CollUtil.isEmpty(page1.getRecords())) return page1;
        page1.getRecords().replaceAll(vo->{
            vo.setMerchantRecvFee(vo.getDeductionTotalFee().subtract(vo.getRefundTotalFee()).subtract(vo.getBankServiceTotalFee()));
            vo.setServiceTypeDesc(ServiceTypeEnum.getTitleByValue(vo.getServiceType()));
            return vo;
        });
        TbBankSettlement sum = mapper.getSum(tbBankSettlement);
        sum.setMerchantRecvFee(sum.getDeductionTotalFee().subtract(sum.getRefundTotalFee()).subtract(sum.getBankServiceTotalFee()));
        page1.getDataMap().put("sum",sum);
        return page1;
    }

    /**
     * 导出数据
     */
    @Override
    public void exportData(HttpServletResponse response,TbBankSettlement tbBankSettlement) {
        List<TbBankSettlement> list = this.mapper.findList(tbBankSettlement);

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("银行预结算表","银行预结算表");

        //设置返回的下载文件名
        String codedFileName = "银行预结算表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

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
                workbook = ExcelExportUtil.exportExcel(params, TbBankSettlement.class, list);
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

        try {
            BigDecimal hundred = BigDecimal.valueOf(100);
            TbBankSettlement sum = list.stream().map(vo -> {
                vo.setMerchantRecvFee(vo.getDeductionTotalFee().subtract(vo.getRefundTotalFee()).subtract(vo.getBankServiceTotalFee()).divide(hundred).setScale(2));
                vo.setDeductionTotalFee(vo.getDeductionTotalFee().divide(hundred).setScale(2));
                vo.setDiscountTotalFee(vo.getDiscountTotalFee().divide(hundred).setScale(2));
                vo.setRefundTotalFee(vo.getRefundTotalFee().divide(hundred).setScale(2));
                vo.setBankServiceTotalFee(vo.getBankServiceTotalFee().divide(hundred).setScale(2));
                vo.setServiceTypeDesc(ServiceTypeEnum.getTitleByValue(vo.getServiceType()));
                return vo;
            }).reduce(new TbBankSettlement(), (v1, v2) -> {
                if(StrUtil.isNotBlank(v1.getBankTransDate())){
                    v1.setMerchantRecvFee(v1.getMerchantRecvFee().add(v2.getMerchantRecvFee()));
                    v1.setDeductionTotalCount(v1.getDeductionTotalCount() + v2.getDeductionTotalCount());
                    v1.setDeductionTotalFee(v1.getDeductionTotalFee().add(v2.getDeductionTotalFee()));
                    v1.setDiscountTotalFee(v1.getDiscountTotalFee().add(v2.getDiscountTotalFee()));
                    v1.setRefundTotalCount(v1.getRefundTotalCount() + v2.getRefundTotalCount());
                    v1.setRefundTotalFee(v1.getRefundTotalFee().add(v2.getRefundTotalFee()));
                    v1.setBankServiceTotalFee(v1.getBankServiceTotalFee().add(v2.getBankServiceTotalFee()));
                }else{
                    v1.setBankTransDate("合计");
                    v1.setMerchantRecvFee(v2.getMerchantRecvFee());
                    v1.setDeductionTotalCount(v2.getDeductionTotalCount());
                    v1.setDeductionTotalFee(v2.getDeductionTotalFee());
                    v1.setDiscountTotalFee(v2.getDiscountTotalFee());
                    v1.setRefundTotalCount(v2.getRefundTotalCount());
                    v1.setRefundTotalFee(v2.getRefundTotalFee());
                    v1.setBankServiceTotalFee(v2.getBankServiceTotalFee());
                }
                return v1;
            });
            list.add(sum);

            int mergeIndex = list.size() + 1;
            workbook = ExcelExportUtil.exportExcel(params, TbBankSettlement.class, list);

            //合并单元格
            Sheet sheet = workbook.getSheet(params.getSheetName());
            ExcelUtil.mergeRegion(sheet, mergeIndex, mergeIndex, 0, 2);

            try {
                if (workbook instanceof HSSFWorkbook) {
                    codedFileName = codedFileName + ".xls";
                } else {
                    codedFileName = codedFileName + ".xlsx";
                }
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(codedFileName, CharsetUtil.UTF_8));
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

    @Override
    public void exportDetailData(HttpServletResponse response, TbBankSettlementDetail detail) {
        List<TbBankSettlementDetail> list = this.mapper.findDetailList(detail);

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("银行预结算明细表","银行预结算明细表");

        //设置返回的下载文件名
        String codedFileName = "银行预结算明细表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

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
                workbook = ExcelExportUtil.exportExcel(params, TbBankSettlementDetail.class, list);
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

        try {
            Map<String, String> allMerchant = merchantManageService.getAllMerchant(Arrays.asList(detail.getMerchantId()), ShiroSecurityUtil.isSuperUser());
            BigDecimal hundred = BigDecimal.valueOf(100);
            TbBankSettlementDetail sum = list.stream().map(vo -> {
                vo.setEexitType(TransactionType.getDescByType(Integer.parseInt(vo.getEexitType())));
                vo.setMerchantGroupName(allMerchant.getOrDefault(vo.getSiteId().substring(0, NodeLevelEnum.MERCHANT_GROUP.getIdLength()),"未知拓展方"));
                vo.setMerchantName(allMerchant.getOrDefault(vo.getSiteId().substring(0,NodeLevelEnum.MERCHANT.getIdLength()),"未知运营方"));
                vo.setSiteName(allMerchant.getOrDefault(vo.getSiteId(),"未知场景"));
                vo.setServiceTypeDesc(ServiceTypeEnum.getTitleByValue(vo.getServiceType()));
                vo.setMerchantRealFee(vo.getMerchantRealFee().divide(hundred).setScale(2));
                vo.setMerchantDiscountFee(vo.getMerchantDiscountFee().divide(hundred).setScale(2));
                return vo;
            }).reduce(new TbBankSettlementDetail(), (v1, v2) -> {
                if(StrUtil.isNotBlank(v1.getEexitType())){
                    v1.setMerchantRealFee(v1.getMerchantRealFee().add(v2.getMerchantRealFee()));
                    v1.setMerchantDiscountFee(v1.getMerchantDiscountFee().add(v2.getMerchantDiscountFee()));
                }else{
                    v1.setEexitType("合计");
                    v1.setMerchantRealFee(v2.getMerchantRealFee());
                    v1.setMerchantDiscountFee(v2.getMerchantDiscountFee());
                }
                return v1;
            });
            list.add(sum);

            int mergeIndex = list.size() + 1;
            workbook = ExcelExportUtil.exportExcel(params, TbBankSettlementDetail.class, list);

            //合并单元格
            Sheet sheet = workbook.getSheet(params.getSheetName());
            ExcelUtil.mergeRegion(sheet, mergeIndex, mergeIndex, 0, 8);

            try {
                if (workbook instanceof HSSFWorkbook) {
                    codedFileName = codedFileName + ".xls";
                } else {
                    codedFileName = codedFileName + ".xlsx";
                }
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(codedFileName, CharsetUtil.UTF_8));
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

    @Override
    public PageVo<TbBankSettlementDetail> detail(PageVo<TbBankSettlementDetail> page, TbBankSettlementDetail detail) {
        PageVo<TbBankSettlementDetail> pageVo = this.mapper.findDetailPage(page,detail);
        if(CollUtil.isEmpty(pageVo.getRecords())) return pageVo;
        Map<String, String> allMerchant = merchantManageService.getAllMerchant(Arrays.asList(detail.getMerchantId()), ShiroSecurityUtil.isSuperUser());
        pageVo.getRecords().replaceAll(vo->{
            vo.setEexitType(TransactionType.getDescByType(Integer.parseInt(vo.getEexitType())));
            vo.setMerchantGroupName(allMerchant.getOrDefault(vo.getSiteId().substring(0, NodeLevelEnum.MERCHANT_GROUP.getIdLength()),"未知拓展方"));
            vo.setMerchantName(allMerchant.getOrDefault(vo.getSiteId().substring(0,NodeLevelEnum.MERCHANT.getIdLength()),"未知运营方"));
            vo.setSiteName(allMerchant.getOrDefault(vo.getSiteId(),"未知场景"));
            vo.setServiceTypeDesc(ServiceTypeEnum.getTitleByValue(vo.getServiceType()));
            return vo;
        });
        TbBankSettlementDetail sum = this.mapper.getDetailSum(detail);
        pageVo.getDataMap().put("sum",sum);
        return pageVo;
    }
}