package com.hgsoft.modules.complaintrefund.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.hgsoft.ecip.auto.poi.excel.ExcelExportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.util.ExcelUtil;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.complaintrefund.entity.*;
import com.hgsoft.modules.complaintrefund.enums.RefundEnum;
import com.hgsoft.modules.complaintrefund.mapper.ComplaintRefundMapper;
import com.hgsoft.modules.complaintrefund.service.ComplaintRefundService;
import com.hgsoft.modules.config.MerchantConfig;
import com.hgsoft.modules.config.PayGatewayConfig;
import com.hgsoft.modules.merchant.enums.ServiceTypeEnum;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.merchantcommon.NodeLevelEnum;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.report.entity.PageVo;
import com.zhanpu.japi.sdk.JapiHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * 投诉退费管理serviceImpl
 * Created by 吴鉴武 on 2021/5/7 16:08
 */
@Service
@Slf4j
public class ComplaintRefundServiceImpl implements ComplaintRefundService {

    @Autowired
    private ComplaintRefundMapper mapper;
    @Autowired
    private MerchantManageService merchantManageService;
    @Resource(name = "businessRedisTemplate")
    private RedisTemplate redisTemplate;
    @Autowired
    private MerchantConfig merchantConfig;
    @Autowired
    private PayGatewayConfig payGatewayConfig;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private JapiHttpClient client;

    @Override
    public PageVo<ComplaintRefund> findPage(PageVo<ComplaintRefund> page, ComplaintRefund complaintRefund) {
        PageVo<ComplaintRefund> iPage = mapper.findPage(page, complaintRefund);
        if (CollUtil.isEmpty(iPage.getRecords())) return iPage;
        Map<String, String> allMerchantInfo = merchantManageService.getAllMerchant(complaintRefund.getUserMerchantParam() == null ? null : complaintRefund.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
        iPage.getRecords().replaceAll(vo -> {
            dataFormat(vo, allMerchantInfo);
            return vo;
        });
        ComplaintRefund sum = mapper.getSum(complaintRefund);
        iPage.getDataMap().put("sum", sum);
        iPage.getDataMap().put("provinceId", merchantConfig.getProvinceId());
        return iPage;
    }

    @Override
    public void exportExcel(HttpServletResponse response, ComplaintRefund complaintRefund) {
        List<ComplaintRefund> list = mapper.findListByConditions(complaintRefund);

        //设置文件title和sheet名字
        ExportParams params = new ExportParams("投诉退费管理表", "投诉退费管理表");

        //设置返回的下载文件名
        String codedFileName = "投诉退费管理表-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

        //设置响应头，返回下载流，下载格式为excel
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        //打开响应输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            log.error("打开响应输出流异常", e);
            return;
        }

        //根据数据创建文件并输出
        Workbook workbook = null;
        if (CollUtil.isEmpty(list)) {
            try {
                workbook = ExcelExportUtil.exportExcel(params, ComplaintRefund.class, list);
                if (workbook instanceof HSSFWorkbook) {
                    codedFileName = codedFileName + ".xls";
                } else {
                    codedFileName = codedFileName + ".xlsx";
                }
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(codedFileName, CharsetUtil.UTF_8));
                workbook.write(outputStream);
                outputStream.flush();
                return;
            } catch (Exception e) {
                log.error("导出Excel文件异常", e);
                return;
            } finally {
                if (outputStream != null) IoUtil.close(outputStream);
                if (workbook != null) IoUtil.close(workbook);
            }
        }

        try {
            Map<String, String> allMerchantInfo = merchantManageService.getAllMerchant(complaintRefund.getUserMerchantParam() == null ? null : complaintRefund.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
            BigDecimal hundred = BigDecimal.valueOf(100);
            ComplaintRefund sum = list.stream().map(vo -> {
                dataFormat(vo, allMerchantInfo);
                vo.setRefundFee(vo.getRefundFee() == null ? null : vo.getRefundFee().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                vo.setActualPayFee(vo.getActualPayFee() == null ? null : vo.getActualPayFee().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                return vo;
            }).reduce(new ComplaintRefund(), (v1, v2) -> {
                if (StrUtil.isNotBlank(v1.getComplaintTime())) {
                    v1.setActualPayFee(v1.getActualPayFee() == null ? v2.getActualPayFee() :
                            v2.getActualPayFee() == null ? v1.getActualPayFee() :
                                    v1.getActualPayFee().add(v2.getActualPayFee()));
                    v1.setRefundFee(v1.getRefundFee() == null ? v2.getRefundFee() :
                            v2.getRefundFee() == null ? v1.getRefundFee() :
                                    v1.getRefundFee().add(v2.getRefundFee()));
                } else {
                    v1.setComplaintTime("合计");
                    v1.setActualPayFee(v2.getActualPayFee());
                    v1.setRefundFee(v2.getRefundFee());
                }
                return v1;
            });
            list.add(sum);

            int mergeIndex = list.size() + 1;
            workbook = ExcelExportUtil.exportExcel(params, ComplaintRefund.class, list);

            //合并单元格
            Sheet sheet = workbook.getSheet(params.getSheetName());
            ExcelUtil.mergeRegion(sheet, mergeIndex, mergeIndex, 0, 14);

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
        } finally {
            if (outputStream != null) IoUtil.close(outputStream);
            if (workbook != null) IoUtil.close(workbook);
        }
    }

    @Override
    public PageVo<OnlineRefundEexit> findRefundEexit(PageVo<OnlineRefundEexit> page, OnlineRefundEexit refundEexit) {
        //黑龙江只有联机退费,山西联机和脱机都有退费
        PageVo<OnlineRefundEexit> ipage = mapper.findRefundEexit(page, refundEexit, merchantConfig.getProvinceId());
        if (CollUtil.isEmpty(ipage.getRecords())) return ipage;
        Map<String, String> allMerchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.userId(), ShiroSecurityUtil.isSuperUser());
        ipage.getRecords().replaceAll(vo -> {
            vo.setMerchantGroupName(allMerchantInfo.getOrDefault(vo.getMerchantGroupId(), "未知拓展方"));
            vo.setMerchantName(allMerchantInfo.getOrDefault(vo.getMerchantId(), "未知运营方"));
            vo.setSiteName(allMerchantInfo.getOrDefault(vo.getSiteId(), "未知场景"));
            vo.setTradeMode(RefundEnum.RefundWayType.getTradeModeByCode(vo.getRefundType()));
            vo.setVehicleColorDesc(VehicleColor.getColorDescByColorNumber(vo.getVehicleColor()));
            vo.setServiceTypeName(ServiceTypeEnum.getTitleByValue(vo.getServiceType()));
            return vo;
        });
        return ipage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Void> saveApplyDraft(RefundApply refundApply) {
        refundApply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.DRAFT.getCode());
        try {
            mapper.saveApply(refundApply);
        } catch (Exception e) {
            return ResultHandler.error("保存草稿发生异常：" + ExceptionUtil.getSimpleMessage(e));
        }
        return ResultHandler.okMsg("保存草稿成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Void> editApplyDraft(RefundApply refundApply) {
        try {
            mapper.updateApply(refundApply);
        } catch (Exception e) {
            return ResultHandler.error("修改保存草稿发生异常：" + ExceptionUtil.getSimpleMessage(e));
        }
        return ResultHandler.okMsg("修改保存草稿成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Void> deleteApplyDraft(Long sysId) {
        try {
            mapper.deleteApply(sysId);
        } catch (Exception e) {
            return ResultHandler.error("删除草稿发生异常：" + ExceptionUtil.getSimpleMessage(e));
        }
        return ResultHandler.okMsg("删除草稿成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public void handleApply(RefundApply refundApply) {
        mapper.handleApply(refundApply);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOnlineRefundEexit(OnlineRefundEexit refundEexit) {
        mapper.saveOnlineRefundEexit(refundEexit);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRefundResult(RefundApply refundApply, OnlineRefundEexit refundEexit) {
        mapper.updateApplyRefundResult(refundApply);
        mapper.updateOnlineRefundResult(refundEexit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Void> saveApply(ComplaintRefund complaintRefund) {
        BigDecimal hundred = BigDecimal.valueOf(100);
        RefundApply apply = complaintRefund.getApply();
        OnlineRefundEexit refundEexit = complaintRefund.getRefundEexit();
        BigDecimal merchantRealFee = refundEexit.getMerchantRealFee().multiply(hundred).setScale(0);
        BigDecimal merchantDiscounFee = refundEexit.getMerchantDiscountFee().multiply(hundred).setScale(0);
        BigDecimal merchantPayFee = refundEexit.getMerchantPayFee().multiply(hundred).setScale(0);
        if (apply != null) {
            //创建提交
            apply.setTransactionId(refundEexit.getTransactionId());
            apply.setMerchantRealFee(merchantRealFee);
            apply.setMerchantDiscountFee(merchantDiscounFee);
            apply.setMerchantPayFee(merchantPayFee);
            apply.setServiceType(refundEexit.getServiceType());
            apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
            try {
                mapper.saveApply(apply);
            } catch (Exception e) {
                return ResultHandler.error("创建提交保存发生异常：" + ExceptionUtil.getSimpleMessage(e));
            }
        } else {
            //修改提交
            try {
                mapper.updateHandleResultAndComplaintTime(refundEexit.getTbRefundApplySysId(), RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
            } catch (Exception e) {
                return ResultHandler.error("修改提交保存发生异常：" + ExceptionUtil.getSimpleMessage(e));
            }
        }
        return ResultHandler.okMsg("提交保存成功");
    }

    @Override
    public ResultBean<Void> handleApplyAndRefundEexit(ComplaintRefund complaintRefund) {
        RefundApply apply = complaintRefund.getApply();
        OnlineRefundEexit refundEexit = complaintRefund.getRefundEexit();
        ComplaintRefundServiceImpl newBean = SpringUtil.getBean(ComplaintRefundServiceImpl.class);
        //判断是否退费
        if (RefundEnum.RefundFlag.NO_REFUND.getCode().equals(apply.getRefundApplyHandleResult())) {
            //更新退费结果为不退费，退费申请时间为当前时间
            apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.DONE_AUDIT.getCode());
            apply.setRefundState(RefundEnum.RefundStatus.NOT_REFUND.getCode());
            try {
                newBean.handleApply(apply);
            } catch (Exception e) {
                return ResultHandler.error("更新退费状态发生异常：" + ExceptionUtil.getSimpleMessage(e));
            }
            return ResultHandler.okMsg("更新为不退费成功");
        } else {
            //判断退费方式
            if (RefundEnum.RefundWayType.UNIONPAY_REFUND.getCode().equals(apply.getRefundWayType())) {
                //先将申请记录更新成退费中
                apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.DONE_AUDIT.getCode());
                apply.setRefundState(RefundEnum.RefundStatus.REFUNDING.getCode());
                BigDecimal hundred = BigDecimal.valueOf(100);
                Date now = new Date();
                BigDecimal merchantRealFee = refundEexit.getMerchantRealFee().multiply(hundred).setScale(0);
                BigDecimal merchantDiscounFee = refundEexit.getMerchantDiscountFee().multiply(hundred).setScale(0);
                BigDecimal merchantPayFee = refundEexit.getMerchantPayFee().multiply(hundred).setScale(0);
                refundEexit.setRefundTransId(refundEexit.getTransactionId() + StrUtil.fillBefore(redisTemplate.boundHashOps("refundTransactionId").increment(refundEexit.getTransactionId(), 1).toString(), '0', 3));
                refundEexit.setRefundFee(merchantRealFee);
                refundEexit.setMerchantPayFee(merchantPayFee);
                refundEexit.setMerchantRealFee(merchantRealFee);
                refundEexit.setMerchantDiscountFee(merchantDiscounFee);
                refundEexit.setUpdateTime(now);
                refundEexit.setCreateTime(now);
                refundEexit.setRefundState(RefundEnum.RefundStatus.REFUNDING.getCode());
                refundEexit.setRefundOrderId(IdUtil.fastSimpleUUID());
                try {
                    newBean.handleApply(apply);
                    newBean.saveOnlineRefundEexit(refundEexit);
                } catch (Exception e) {
                    return ResultHandler.error("更新退费申请表以及插入在线退费流水表发生异常：" + ExceptionUtil.getSimpleMessage(e));
                }
                //发起退费并返回结果
                return refund(apply, refundEexit, newBean);
            } else {
                //更新退费结果为退费成功，退费申请时间为当前时间
                try {
                    apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.DONE_AUDIT.getCode());
                    apply.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
                    newBean.handleApply(apply);
                } catch (Exception e) {
                    return ResultHandler.error("更新退费结果发生异常：" + ExceptionUtil.getSimpleMessage(e));
                }
                return ResultHandler.okMsg("退费成功");
            }
        }
    }

    @Override
    public ResultBean<Void> searchRefundResult(Long sysId) {
        //获取退费申请记录和在线退费流水记录
        RefundApply applyById = mapper.getApplyById(sysId);
        OnlineRefundEexit refundEexit = mapper.getOnlineRefundEexitById(applyById.getTransactionId(), RefundEnum.RefundStatus.REFUNDING.getCode());
        applyById.setSysId(refundEexit.getTbRefundApplySysId());
        //向支付网关发起退费查询
        ExpDeductionRespond respond;
        try {
            respond = client.get(StrUtil.format(payGatewayConfig.getQueryRefundResultUri(), 2, refundEexit.getRefundOrderId()), null, ExpDeductionRespond.class);
        } catch (Exception e) {
            String message = ExceptionUtil.getSimpleMessage(e);
            return ResultHandler.error("请求支付网关查询退费结果发生异常：" + message);
        }
        //更新退费结果
        ComplaintRefundServiceImpl bean = SpringUtil.getBean(ComplaintRefundServiceImpl.class);
        if (respond.isSuccess()) {
            //更新申请表状态为退费成功、在线流水退费表状态为退费成功
            applyById.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
            refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
            refundEexit.setBankRefundMsg(respond.getReturn_msg());
            refundEexit.setRefundCompleteTime(DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
            try {
                bean.updateRefundResult(applyById, refundEexit);
            } catch (Exception e) {
                return ResultHandler.error("更新退费结果发生异常：" + ExceptionUtil.getSimpleMessage(e));
            }
            //返回响应
            return ResultHandler.okMsg("退费成功");
        } else {
            switch (respond.getReturn_code()) {
                case "NOT_FOUND":
                    //重新发起退费
                    return refund(applyById, refundEexit, bean);
                case "FAILURE":
                    //更新退费申请表状态为退费失败、审核状态为待审核，更新在线退费流水表状态为退费失败
                    applyById.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    applyById.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
                    refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    refundEexit.setBankRefundMsg(respond.getReturn_msg());
                    refundEexit.setRefundCompleteTime(StrUtil.hasBlank(respond.getTrx_date(), respond.getTrx_time()) ? null : DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
                    try {
                        bean.updateRefundResult(applyById, refundEexit);
                    } catch (Exception e) {
                        return ResultHandler.error("更新退费结果发生异常：" + ExceptionUtil.getSimpleMessage(e));
                    }
                    //返回响应
                    return ResultHandler.okMsg("退费失败,支付网关响应信息:" + respond.getReturn_msg());
                case "REFUNDING":
                    //不做操作
                    return ResultHandler.okMsg("退费中，暂未知退费结果");
                default:
                    return ResultHandler.error("支付网关返回未知信息:" + JSONUtil.toJsonStr(respond));
            }
        }
    }

    @Override
    public ComplaintRefund getDetail(Long sysId, String method) {
        RefundApply apply = mapper.getApplyById(sysId);
        OnlineRefundEexit refundEexitOne = mapper.findRefundEexitOne(apply.getTransactionId());
        Assert.isFalse("handle".equals(method) && refundEexitOne == null, "流水号{}找不到对应的流水记录", apply.getTransactionId());
        if (refundEexitOne != null) {
            apply.setFeedBackTradeTime(refundEexitOne.getTradeDay().substring(0, 10));
            if (RefundEnum.RefundApplyHandlerResult.DONE_AUDIT.getCode().equals(apply.getRefundApplyHandleResult()) && !RefundEnum.RefundStatus.NOT_REFUND.getCode().equals(apply.getRefundState())) {
                apply.setRefundApplyHandleResult(RefundEnum.RefundFlag.REFUND.getCode());
            }
            refundEexitOne.setTradeMode(RefundEnum.RefundWayType.getTradeModeByCode(refundEexitOne.getRefundType()));
            Map<String, String> allMerchantInfo = merchantManageService.getAllMerchant(Arrays.asList(refundEexitOne.getSiteId()), ShiroSecurityUtil.isSuperUser());
            refundEexitOne.setMerchantGroupName(allMerchantInfo.getOrDefault(refundEexitOne.getMerchantGroupId(), "未知拓展方"));
            refundEexitOne.setMerchantName(allMerchantInfo.getOrDefault(refundEexitOne.getMerchantId(), "未知运营方"));
            refundEexitOne.setSiteName(allMerchantInfo.getOrDefault(refundEexitOne.getSiteId(), "未知场景"));
        }
        ComplaintRefund complaintRefund = new ComplaintRefund();
        complaintRefund.setApply(apply);
        complaintRefund.setRefundEexit(refundEexitOne);
        return complaintRefund;
    }

    /**
     * 数据格式化
     *
     * @param complaintRefund
     * @param allMerchantInfo
     */
    private void dataFormat(ComplaintRefund complaintRefund, Map<String, String> allMerchantInfo) {
        complaintRefund.setServiceTypeDesc(ServiceTypeEnum.getTitleByValue(complaintRefund.getServiceType()));
        complaintRefund.setMerchantGroupName(allMerchantInfo.getOrDefault(complaintRefund.getSiteId().substring(0, NodeLevelEnum.MERCHANT_GROUP.getIdLength()), "未知拓展方"));
        complaintRefund.setMerchantName(allMerchantInfo.getOrDefault(complaintRefund.getSiteId().substring(0, NodeLevelEnum.MERCHANT.getIdLength()), "未知运营方"));
        complaintRefund.setSiteName(allMerchantInfo.getOrDefault(complaintRefund.getSiteId(), "未知场景"));
        complaintRefund.setRefundFlagDesc(RefundEnum.RefundApplyHandlerResult.getDescByCode(complaintRefund.getRefundApplyHandlerResult()));
        complaintRefund.setRefundStatusDesc(RefundEnum.RefundStatus.getDescByCode(complaintRefund.getRefundStatus()));
        complaintRefund.setRefundTypeDesc(RefundEnum.RefundWayType.getDescByCode(complaintRefund.getRefundType()));
        complaintRefund.setComplaintChannelDesc(RefundEnum.Channel.getNameByCode(complaintRefund.getComplaintChannel()));
        complaintRefund.setVehicleColorDesc(VehicleColor.getColorDescByColorNumber(complaintRefund.getVehicleColor()));
    }

    /**
     * 向支付网关发起退费
     *
     * @param apply
     * @param refundEexit
     * @param newBean
     * @return
     */
    private ResultBean<Void> refund(RefundApply apply, OnlineRefundEexit refundEexit, ComplaintRefundServiceImpl newBean) {
        //请求支付网关退费
        Map<String, Object> param = new HashMap<>();
        param.put("trx_serno", refundEexit.getRefundOrderId());
        param.put("otrx_serno", refundEexit.getDeductionOrderId());   //扣款交易流水号
        param.put("trx_amt", refundEexit.getMerchantRealFee());
        ExpRefundRespond respond;
        try {
            respond = client.post(payGatewayConfig.getRefundUri(), param, ExpRefundRespond.class);
        } catch (Exception e) {
            String message = ExceptionUtil.getSimpleMessage(e);
            return ResultHandler.error("请求支付网关退费发生异常：" + message);
        }
        try {
            if (respond.isSuccess()) {
                //更新申请表状态为退费成功、在线流水退费表状态为退费成功
                apply.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
                refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
                refundEexit.setBankRefundMsg(respond.getReturn_msg());
                refundEexit.setRefundCompleteTime(DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
                newBean.updateRefundResult(apply, refundEexit);
                return ResultHandler.okMsg("退费成功");
            } else {
                String return_code = respond.getReturn_code();
                if ("FAILURE".equals(return_code)) {
                    //更新退费申请表状态为退费失败、审核状态为待审核，更新在线退费流水表状态为退费失败
                    apply.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
                    refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    refundEexit.setBankRefundMsg(respond.getReturn_msg());
                    refundEexit.setRefundCompleteTime(StrUtil.hasBlank(respond.getTrx_date(), respond.getTrx_time()) ? null : DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
                    newBean.updateRefundResult(apply, refundEexit);
                    return ResultHandler.okMsg("退费失败,支付网关响应信息:" + respond.getReturn_msg());
                } else if ("REFUNDING".equals(return_code)) {
                    //不做操作
                    return ResultHandler.okMsg("退费中，暂未知退费结果");
                } else {
                    return ResultHandler.error("支付网关返回未知信息:" + JSONUtil.toJsonStr(respond));
                }
            }
        } catch (Exception e) {
            return ResultHandler.error("更新退费结果发生异常：" + ExceptionUtil.getSimpleMessage(e));
        }
    }
}
