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
 * ??????????????????serviceImpl
 * Created by ????????? on 2021/5/7 16:08
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

        //????????????title???sheet??????
        ExportParams params = new ExportParams("?????????????????????", "?????????????????????");

        //??????????????????????????????
        String codedFileName = "?????????????????????-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);

        //???????????????????????????????????????????????????excel
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        //?????????????????????
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            log.error("???????????????????????????", e);
            return;
        }

        //?????????????????????????????????
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
                log.error("??????Excel????????????", e);
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
                    v1.setComplaintTime("??????");
                    v1.setActualPayFee(v2.getActualPayFee());
                    v1.setRefundFee(v2.getRefundFee());
                }
                return v1;
            });
            list.add(sum);

            int mergeIndex = list.size() + 1;
            workbook = ExcelExportUtil.exportExcel(params, ComplaintRefund.class, list);

            //???????????????
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
                log.error("??????Excel????????????", e);
            }
        } finally {
            if (outputStream != null) IoUtil.close(outputStream);
            if (workbook != null) IoUtil.close(workbook);
        }
    }

    @Override
    public PageVo<OnlineRefundEexit> findRefundEexit(PageVo<OnlineRefundEexit> page, OnlineRefundEexit refundEexit) {
        //???????????????????????????,?????????????????????????????????
        PageVo<OnlineRefundEexit> ipage = mapper.findRefundEexit(page, refundEexit, merchantConfig.getProvinceId());
        if (CollUtil.isEmpty(ipage.getRecords())) return ipage;
        Map<String, String> allMerchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.userId(), ShiroSecurityUtil.isSuperUser());
        ipage.getRecords().replaceAll(vo -> {
            vo.setMerchantGroupName(allMerchantInfo.getOrDefault(vo.getMerchantGroupId(), "???????????????"));
            vo.setMerchantName(allMerchantInfo.getOrDefault(vo.getMerchantId(), "???????????????"));
            vo.setSiteName(allMerchantInfo.getOrDefault(vo.getSiteId(), "????????????"));
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
            return ResultHandler.error("???????????????????????????" + ExceptionUtil.getSimpleMessage(e));
        }
        return ResultHandler.okMsg("??????????????????");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Void> editApplyDraft(RefundApply refundApply) {
        try {
            mapper.updateApply(refundApply);
        } catch (Exception e) {
            return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
        }
        return ResultHandler.okMsg("????????????????????????");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Void> deleteApplyDraft(Long sysId) {
        try {
            mapper.deleteApply(sysId);
        } catch (Exception e) {
            return ResultHandler.error("???????????????????????????" + ExceptionUtil.getSimpleMessage(e));
        }
        return ResultHandler.okMsg("??????????????????");
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
            //????????????
            apply.setTransactionId(refundEexit.getTransactionId());
            apply.setMerchantRealFee(merchantRealFee);
            apply.setMerchantDiscountFee(merchantDiscounFee);
            apply.setMerchantPayFee(merchantPayFee);
            apply.setServiceType(refundEexit.getServiceType());
            apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
            try {
                mapper.saveApply(apply);
            } catch (Exception e) {
                return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
            }
        } else {
            //????????????
            try {
                mapper.updateHandleResultAndComplaintTime(refundEexit.getTbRefundApplySysId(), RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
            } catch (Exception e) {
                return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
            }
        }
        return ResultHandler.okMsg("??????????????????");
    }

    @Override
    public ResultBean<Void> handleApplyAndRefundEexit(ComplaintRefund complaintRefund) {
        RefundApply apply = complaintRefund.getApply();
        OnlineRefundEexit refundEexit = complaintRefund.getRefundEexit();
        ComplaintRefundServiceImpl newBean = SpringUtil.getBean(ComplaintRefundServiceImpl.class);
        //??????????????????
        if (RefundEnum.RefundFlag.NO_REFUND.getCode().equals(apply.getRefundApplyHandleResult())) {
            //??????????????????????????????????????????????????????????????????
            apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.DONE_AUDIT.getCode());
            apply.setRefundState(RefundEnum.RefundStatus.NOT_REFUND.getCode());
            try {
                newBean.handleApply(apply);
            } catch (Exception e) {
                return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
            }
            return ResultHandler.okMsg("????????????????????????");
        } else {
            //??????????????????
            if (RefundEnum.RefundWayType.UNIONPAY_REFUND.getCode().equals(apply.getRefundWayType())) {
                //????????????????????????????????????
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
                    return ResultHandler.error("?????????????????????????????????????????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
                }
                //???????????????????????????
                return refund(apply, refundEexit, newBean);
            } else {
                //?????????????????????????????????????????????????????????????????????
                try {
                    apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.DONE_AUDIT.getCode());
                    apply.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
                    newBean.handleApply(apply);
                } catch (Exception e) {
                    return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
                }
                return ResultHandler.okMsg("????????????");
            }
        }
    }

    @Override
    public ResultBean<Void> searchRefundResult(Long sysId) {
        //???????????????????????????????????????????????????
        RefundApply applyById = mapper.getApplyById(sysId);
        OnlineRefundEexit refundEexit = mapper.getOnlineRefundEexitById(applyById.getTransactionId(), RefundEnum.RefundStatus.REFUNDING.getCode());
        applyById.setSysId(refundEexit.getTbRefundApplySysId());
        //?????????????????????????????????
        ExpDeductionRespond respond;
        try {
            respond = client.get(StrUtil.format(payGatewayConfig.getQueryRefundResultUri(), 2, refundEexit.getRefundOrderId()), null, ExpDeductionRespond.class);
        } catch (Exception e) {
            String message = ExceptionUtil.getSimpleMessage(e);
            return ResultHandler.error("???????????????????????????????????????????????????" + message);
        }
        //??????????????????
        ComplaintRefundServiceImpl bean = SpringUtil.getBean(ComplaintRefundServiceImpl.class);
        if (respond.isSuccess()) {
            //?????????????????????????????????????????????????????????????????????????????????
            applyById.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
            refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
            refundEexit.setBankRefundMsg(respond.getReturn_msg());
            refundEexit.setRefundCompleteTime(DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
            try {
                bean.updateRefundResult(applyById, refundEexit);
            } catch (Exception e) {
                return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
            }
            //????????????
            return ResultHandler.okMsg("????????????");
        } else {
            switch (respond.getReturn_code()) {
                case "NOT_FOUND":
                    //??????????????????
                    return refund(applyById, refundEexit, bean);
                case "FAILURE":
                    //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    applyById.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    applyById.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
                    refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    refundEexit.setBankRefundMsg(respond.getReturn_msg());
                    refundEexit.setRefundCompleteTime(StrUtil.hasBlank(respond.getTrx_date(), respond.getTrx_time()) ? null : DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
                    try {
                        bean.updateRefundResult(applyById, refundEexit);
                    } catch (Exception e) {
                        return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
                    }
                    //????????????
                    return ResultHandler.okMsg("????????????,????????????????????????:" + respond.getReturn_msg());
                case "REFUNDING":
                    //????????????
                    return ResultHandler.okMsg("?????????????????????????????????");
                default:
                    return ResultHandler.error("??????????????????????????????:" + JSONUtil.toJsonStr(respond));
            }
        }
    }

    @Override
    public ComplaintRefund getDetail(Long sysId, String method) {
        RefundApply apply = mapper.getApplyById(sysId);
        OnlineRefundEexit refundEexitOne = mapper.findRefundEexitOne(apply.getTransactionId());
        Assert.isFalse("handle".equals(method) && refundEexitOne == null, "?????????{}??????????????????????????????", apply.getTransactionId());
        if (refundEexitOne != null) {
            apply.setFeedBackTradeTime(refundEexitOne.getTradeDay().substring(0, 10));
            if (RefundEnum.RefundApplyHandlerResult.DONE_AUDIT.getCode().equals(apply.getRefundApplyHandleResult()) && !RefundEnum.RefundStatus.NOT_REFUND.getCode().equals(apply.getRefundState())) {
                apply.setRefundApplyHandleResult(RefundEnum.RefundFlag.REFUND.getCode());
            }
            refundEexitOne.setTradeMode(RefundEnum.RefundWayType.getTradeModeByCode(refundEexitOne.getRefundType()));
            Map<String, String> allMerchantInfo = merchantManageService.getAllMerchant(Arrays.asList(refundEexitOne.getSiteId()), ShiroSecurityUtil.isSuperUser());
            refundEexitOne.setMerchantGroupName(allMerchantInfo.getOrDefault(refundEexitOne.getMerchantGroupId(), "???????????????"));
            refundEexitOne.setMerchantName(allMerchantInfo.getOrDefault(refundEexitOne.getMerchantId(), "???????????????"));
            refundEexitOne.setSiteName(allMerchantInfo.getOrDefault(refundEexitOne.getSiteId(), "????????????"));
        }
        ComplaintRefund complaintRefund = new ComplaintRefund();
        complaintRefund.setApply(apply);
        complaintRefund.setRefundEexit(refundEexitOne);
        return complaintRefund;
    }

    /**
     * ???????????????
     *
     * @param complaintRefund
     * @param allMerchantInfo
     */
    private void dataFormat(ComplaintRefund complaintRefund, Map<String, String> allMerchantInfo) {
        complaintRefund.setServiceTypeDesc(ServiceTypeEnum.getTitleByValue(complaintRefund.getServiceType()));
        complaintRefund.setMerchantGroupName(allMerchantInfo.getOrDefault(complaintRefund.getSiteId().substring(0, NodeLevelEnum.MERCHANT_GROUP.getIdLength()), "???????????????"));
        complaintRefund.setMerchantName(allMerchantInfo.getOrDefault(complaintRefund.getSiteId().substring(0, NodeLevelEnum.MERCHANT.getIdLength()), "???????????????"));
        complaintRefund.setSiteName(allMerchantInfo.getOrDefault(complaintRefund.getSiteId(), "????????????"));
        complaintRefund.setRefundFlagDesc(RefundEnum.RefundApplyHandlerResult.getDescByCode(complaintRefund.getRefundApplyHandlerResult()));
        complaintRefund.setRefundStatusDesc(RefundEnum.RefundStatus.getDescByCode(complaintRefund.getRefundStatus()));
        complaintRefund.setRefundTypeDesc(RefundEnum.RefundWayType.getDescByCode(complaintRefund.getRefundType()));
        complaintRefund.setComplaintChannelDesc(RefundEnum.Channel.getNameByCode(complaintRefund.getComplaintChannel()));
        complaintRefund.setVehicleColorDesc(VehicleColor.getColorDescByColorNumber(complaintRefund.getVehicleColor()));
    }

    /**
     * ???????????????????????????
     *
     * @param apply
     * @param refundEexit
     * @param newBean
     * @return
     */
    private ResultBean<Void> refund(RefundApply apply, OnlineRefundEexit refundEexit, ComplaintRefundServiceImpl newBean) {
        //????????????????????????
        Map<String, Object> param = new HashMap<>();
        param.put("trx_serno", refundEexit.getRefundOrderId());
        param.put("otrx_serno", refundEexit.getDeductionOrderId());   //?????????????????????
        param.put("trx_amt", refundEexit.getMerchantRealFee());
        ExpRefundRespond respond;
        try {
            respond = client.post(payGatewayConfig.getRefundUri(), param, ExpRefundRespond.class);
        } catch (Exception e) {
            String message = ExceptionUtil.getSimpleMessage(e);
            return ResultHandler.error("???????????????????????????????????????" + message);
        }
        try {
            if (respond.isSuccess()) {
                //?????????????????????????????????????????????????????????????????????????????????
                apply.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
                refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_SUCCESS.getCode());
                refundEexit.setBankRefundMsg(respond.getReturn_msg());
                refundEexit.setRefundCompleteTime(DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
                newBean.updateRefundResult(apply, refundEexit);
                return ResultHandler.okMsg("????????????");
            } else {
                String return_code = respond.getReturn_code();
                if ("FAILURE".equals(return_code)) {
                    //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    apply.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    apply.setRefundApplyHandleResult(RefundEnum.RefundApplyHandlerResult.WAIT_AUDIT.getCode());
                    refundEexit.setRefundState(RefundEnum.RefundStatus.REFUND_FAIL.getCode());
                    refundEexit.setBankRefundMsg(respond.getReturn_msg());
                    refundEexit.setRefundCompleteTime(StrUtil.hasBlank(respond.getTrx_date(), respond.getTrx_time()) ? null : DateUtil.parse(respond.getTrx_date() + "T" + respond.getTrx_time(), DatePattern.UTC_SIMPLE_PATTERN));
                    newBean.updateRefundResult(apply, refundEexit);
                    return ResultHandler.okMsg("????????????,????????????????????????:" + respond.getReturn_msg());
                } else if ("REFUNDING".equals(return_code)) {
                    //????????????
                    return ResultHandler.okMsg("?????????????????????????????????");
                } else {
                    return ResultHandler.error("??????????????????????????????:" + JSONUtil.toJsonStr(respond));
                }
            }
        } catch (Exception e) {
            return ResultHandler.error("?????????????????????????????????" + ExceptionUtil.getSimpleMessage(e));
        }
    }
}
