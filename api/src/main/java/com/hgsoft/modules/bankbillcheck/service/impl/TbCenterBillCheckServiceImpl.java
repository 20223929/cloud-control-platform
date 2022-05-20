package com.hgsoft.modules.bankbillcheck.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckSearchVo;
import com.hgsoft.modules.bankbillcheck.entity.TbEtcTransactionEexit;
import com.hgsoft.modules.bankbillcheck.entity.TbEtcTransactionEexitExportExeclVo;
import com.hgsoft.modules.bankbillcheck.mapper.TbCenterBillCheckMapper;
import com.hgsoft.modules.bankbillcheck.service.TbCenterBillCheckService;
import com.hgsoft.modules.merchant.service.SysUserMerchantService;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


/**
 * 联网中心对账核对表ServiceImpl
 *
 * @author 何志豪
 * @version 2021-04-21 02:30:17
 */

@Service("com.hgsoft.modules.bankbillcheck.service.TbCenterBillCheckService")
public class TbCenterBillCheckServiceImpl extends CrudServiceImpl<TbCenterBillCheckMapper, TbCenterBillCheck> implements TbCenterBillCheckService {

    @Autowired
    private MerchantManageService merchantManageService;

    @Autowired
    private SysUserMerchantService sysUserMerchantService;

    private final String[] tipString = {
            "transDate(【主键transDate】): 可以不填写主键transDate；",
            "merchantGroupId(【主键merchantGroupId】): 可以不填写主键merchantGroupId；",
            "merchantId(【主键merchantId】): 可以不填写主键merchantId；",
            "siteId(【主键siteId】): 可以不填写主键siteId；",
            "transDate(【银行对账交易日期】): 必填",
            "merchantGroupId(【拓展方】): 必填",
            "merchantId(【运营方】): 必填",
            "siteId(【场景】): 必填",
            "confirmTotalCount(【用户确认流水数量】): 必填",
            "confirmTotalAmount(【用户确认流水金额(分)】): 必填",
            "confirmMan(【确认人】): 非必填",
            "confirmTime(【确认时间】): 必填",
            "remark(【备注】): 非必填",
            "secondConfirmMan(【补确认人】): 非必填",
            "secondConfirmTime(【补确认时间】): 必填",
            "secondConfirmRemark(【补确认备注】): 非必填",
            "remarks(【备注信息】): 非必填"
    };


    @Override
    public TbCenterBillCheck getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId) {
        return this.getOne(new LambdaQueryWrapper<TbCenterBillCheck>()
                .eq(TbCenterBillCheck::getTransDate, transDate)
                .eq(TbCenterBillCheck::getMerchantGroupId, merchantGroupId)
                .eq(TbCenterBillCheck::getMerchantId, merchantId)
                .eq(TbCenterBillCheck::getSiteId, siteId));
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbCenterBillCheckSearchVo tbCenterBillCheck) {
        List<TbCenterBillCheck> list = this.findSearchDataAll(tbCenterBillCheck);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心对账核对表-" + yyyyMMddHHmmss );
        mv.addObject(NormalExcelConstants.CLASS, TbCenterBillCheck.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心对账核对表", "联网中心对账核对"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
     * 导出模板
     */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心对账核对模板");
        mv.addObject(NormalExcelConstants.CLASS, TbCenterBillCheck.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心对账核对表", "联网中心对账核对"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public IPage<TbCenterBillCheck> findSearchDataPage(Page<TbCenterBillCheck> page, TbCenterBillCheckSearchVo entity) {
        IPage<TbCenterBillCheck> searchDataPage = this.mapper.findSearchDataPage(page, entity);
        decorate(searchDataPage.getRecords().iterator());
        addSubTotal(searchDataPage.getRecords());
        addSumTotal(searchDataPage.getRecords(), entity);
        return searchDataPage;
    }

    @Override
    public List<TbCenterBillCheck> findSearchDataAll(TbCenterBillCheckSearchVo entity) {
        List<TbCenterBillCheck> searchDataAll = this.mapper.findSearchDataAll(entity);
        decorate(searchDataAll.iterator());
        addSumTotal(searchDataAll, entity);
        return searchDataAll;
    }

    @Override
    public void confirm(TbCenterBillCheckSearchVo entity) {
        this.mapper.insertSumRecord(entity);
        this.mapper.insertDetailRecord(entity);
    }

    @Override
    public void secondConfirm(TbCenterBillCheckSearchVo entity) {
        this.mapper.updateSumForSecondConfirm(entity);
        this.mapper.updateDetailForSecondConfirm(entity);
    }

    @Override
    public Long countEexit(TbCenterBillCheckSearchVo vo) {
        return this.mapper.findAllEexitCount(vo);
    }

    @Override
    public ModelAndView exportEexitDetailData(HttpServletRequest request, TbCenterBillCheckSearchVo vo) {
        List<TbEtcTransactionEexit> list = this.mapper.findAllEexit(vo);

        List<TbEtcTransactionEexitExportExeclVo> exports = new ArrayList<>(list.size());
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        for (TbEtcTransactionEexit c : list) {
            TbEtcTransactionEexitExportExeclVo eexitExportExeclVo = TbEtcTransactionEexitExportExeclVo.transform(c, merchantInfo);
            exports.add(eexitExportExeclVo);
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心对账核流水明细-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TbEtcTransactionEexitExportExeclVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心对账核对明细表", "联网中心对账核对明细表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exports);
        return mv;
    }

    private void decorate(Iterator<TbCenterBillCheck> collection) {
        ShiroUser principal = ShiroSecurityUtil.getPrincipal();
        String userId = principal.getId();
        Boolean isSuperUser = principal.getIsSuperUser();
        Map<String, String> merchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        while (collection.hasNext()) {
            decorate(collection.next(), merchantByUserId);
        }
    }

    private static final String[] confrimStateArray = new String[]{"待确认", "已确认", "待补确认", "已补确认"};

    private void decorate(TbCenterBillCheck billCheck, Map<String, String> merchantInfo) {
        String merchantGroupId = billCheck.getMerchantGroupId();
        String merchantId = billCheck.getMerchantId();
        String siteId = billCheck.getSiteId();
        Integer confirmState = billCheck.getConfirmState();
        String merchantGroupIdName = merchantInfo.get(merchantGroupId);
        String merchantIdName = merchantInfo.get(merchantId);
        String siteIdName = merchantInfo.get(siteId);
        billCheck.setMerchantGroupIdName(merchantGroupIdName == null ? "未知拓展方" : merchantGroupIdName);
        billCheck.setMerchantIdName(merchantIdName == null ? "未知运营方" : merchantIdName);
        billCheck.setSiteIdName(siteIdName == null ? "未知场景" : siteIdName);
        billCheck.setConfirmStateStr(confrimStateArray[confirmState]);

        Long centerTotalAmount = billCheck.getCenterTotalAmount() == null ? 0 : billCheck.getCenterTotalAmount();
        Long confirmTotalAmount = billCheck.getConfirmTotalAmount() == null ? 0 : billCheck.getConfirmTotalAmount();
        Long centerTotalCount = billCheck.getCenterTotalCount() == null ? 0 : billCheck.getCenterTotalCount();
        Long confirmTotalCount = billCheck.getConfirmTotalCount() == null ? 0 : billCheck.getConfirmTotalCount();
        billCheck.setConfirmTotalCount(confirmTotalCount);
        billCheck.setConfirmTotalAmount(confirmTotalAmount);
        billCheck.setCenterTotalCount(centerTotalCount);
        billCheck.setCenterTotalAmount(centerTotalAmount);
        billCheck.setConfirmTotalAmountStr(BigDecimal.valueOf(confirmTotalAmount).movePointLeft(2).toString());
        billCheck.setCenterTotalAmountStr(BigDecimal.valueOf(centerTotalAmount).movePointLeft(2).toString());
    }

    private void addSubTotal(List<TbCenterBillCheck> list) {
        if (list == null || list.isEmpty())
            return;
        Long confirmTotalCount = 0L;
        Long centerTotalCount = 0L;
        Long confirmTotalAmount = 0L;
        Long centerTotalAmount = 0L;


        for (TbCenterBillCheck c : list) {
            Long confirmTotalCount1 = c.getConfirmTotalCount();
            confirmTotalCount += confirmTotalCount1 == null ? 0 : confirmTotalCount1;
            Long centerTotalCount1 = c.getCenterTotalCount();
            centerTotalCount += centerTotalCount1 == null ? 0 : centerTotalCount1;
            Long confirmTotalAmount1 = c.getConfirmTotalAmount();
            confirmTotalAmount += confirmTotalAmount1 == null ? 0 : confirmTotalAmount1;
            Long centerTotalAmount1 = c.getCenterTotalAmount();
            centerTotalAmount += centerTotalAmount1 == null ? 0 : centerTotalAmount1;
        }
        TbCenterBillCheck subTotal = new TbCenterBillCheck();
        subTotal.setMerchantGroupIdName("小计");
        subTotal.setConfirmTotalCount(confirmTotalCount);
        subTotal.setConfirmTotalAmount(confirmTotalAmount);
        subTotal.setCenterTotalCount(centerTotalCount);
        subTotal.setCenterTotalAmount(centerTotalAmount);
        subTotal.setConfirmTotalAmountStr(BigDecimal.valueOf(confirmTotalAmount).movePointLeft(2).toString());
        subTotal.setCenterTotalAmountStr(BigDecimal.valueOf(centerTotalAmount).movePointLeft(2).toString());
        list.add(subTotal);
    }

    private void addSumTotal(List<TbCenterBillCheck> list, TbCenterBillCheckSearchVo billCheck) {
        TbCenterBillCheck sum = this.mapper.getSum(billCheck);

        if (sum != null) {

            Long centerTotalAmount = sum.getCenterTotalAmount() == null ? 0 : sum.getCenterTotalAmount();
            Long confirmTotalAmount = sum.getConfirmTotalAmount() == null ? 0 : sum.getConfirmTotalAmount();
            Long centerTotalCount = sum.getCenterTotalCount() == null ? 0 : sum.getCenterTotalCount();
            Long confirmTotalCount = sum.getConfirmTotalCount() == null ? 0 : sum.getConfirmTotalCount();
            sum.setConfirmTotalCount(confirmTotalCount);
            sum.setConfirmTotalAmount(confirmTotalAmount);
            sum.setCenterTotalCount(centerTotalCount);
            sum.setCenterTotalAmount(centerTotalAmount);
            sum.setConfirmTotalAmountStr(BigDecimal.valueOf(confirmTotalAmount).movePointLeft(2).toString());
            sum.setCenterTotalAmountStr(BigDecimal.valueOf(centerTotalAmount).movePointLeft(2).toString());
            sum.setMerchantGroupIdName("合计");
            list.add(sum);
        }
    }
}