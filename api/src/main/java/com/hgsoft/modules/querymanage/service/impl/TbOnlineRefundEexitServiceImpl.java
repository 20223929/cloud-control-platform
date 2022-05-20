package com.hgsoft.modules.querymanage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.ecip.web.util.SystemUtils;
import com.hgsoft.modules.config.MerchantConfig;
import com.hgsoft.modules.merchant.enums.ServiceTypeEnum;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.querymanage.entity.TbOnlineRefundEexit;
import com.hgsoft.modules.querymanage.enums.RefundStateEnum;
import com.hgsoft.modules.querymanage.enums.RefundTypeEnum;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.querymanage.mapper.TbOnlineRefundEexitMapper;
import com.hgsoft.modules.querymanage.service.TbOnlineRefundEexitService;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * 银行退费流水查询ServiceImpl
 * @author 雷新辉
 * @version 2021-04-22 09:25:22
 */

@Service("com.hgsoft.modules.querymanage.service.TbOnlineRefundEexitService")
public class TbOnlineRefundEexitServiceImpl extends CrudServiceImpl<TbOnlineRefundEexitMapper, TbOnlineRefundEexit> implements TbOnlineRefundEexitService {

    private final String[] tipString = {
        "sysId(【主键sysId】): 可以不填写主键sysId；",
        "sysId(【系统编号】): 必填",
        "refundTime(【退费日期】): 必填",
        "merchantGroupIdName(【拓展方】): 必填",
        "merchantIdName(【运营方】): 必填",
        "siteIdName(【场景】): 必填",
        "refundType(【退费类型】): 必填",
        "refundTypeName(【退费类型】): 必填",
        "serviceTypeName(【服务类型】): 必填",
        "refundTransId(【退费流水号】): 必填",
        "transactionId(【交易流水号】): 必填",
        "merchantGroupId(【拓展方】): 必填",
        "merchantId(【运营方】): 必填",
        "siteId(【场景】): 必填",
        "enTime(【入场时间】): 必填",
        "exTime(【出场时间】): 必填",
        "transTime(【原交易时间】): 必填",
        "vehiclePlate(【车牌号码】): 必填",
        "serviceType(【服务类型】): 必填",
        "merchantDiscountFee(【优惠金额】): 必填",
        "refundOrderId(【退费订单号】): 非必填",
        "bankRefundOrderId(【银行退费订单号】): 非必填",
        "refundFee(【退费金额】): 必填",
        "bankDeductionOrderId(【银行订单号】): 必填",
        "refundState(【退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败】): 必填",
        "tbRefundApplySysId(【退费申请表_系统编号】): 必填",
        "equipmentId(【四级设备编号】): 必填",
        "vehicleColor(【车牌颜色】): 必填",
        "vehicleType(【车型】): 必填",
        "merchantPayFee(【应收金额】): 必填",
        "merchantRealFee(【实收金额】): 必填",
        "merchantSettlementFee(【商户结算金额】): 必填",
        "clearServiceFee(【清算服务费】): 必填",
        "clearServiceFeeRate(【清算服务费费率】): 必填",
        "bankServiceFee(【银行手续费】): 必填",
        "bankServiceFeeRate(【银行手续费费率】): 必填",
        "updateTime(【更新时间】): 非必填",
        "bankRefundMsg(【银行退费描述】): 非必填",
        "refundCompleteTime(【退费完成时间】): 非必填",
        "billResult(【对账结果】): 非必填",
        "billTime(【对账时间】): 非必填",
        "deductionTime(【扣款时间】): 必填",
        "createTime(【创建时间】): 必填",
        "remarks(【备注信息】): 非必填"
    };
    @Autowired
    private MerchantManageService merchantManageService;
    @Autowired
    private MerchantConfig merchantConfig;

    /**
     * 分页查询
     * @param page
     * @param tbOnlineRefundEexit
     * @return
     */
    @Override
    public IPage<TbOnlineRefundEexit> tbOnlineRefundEexitPage(PageVo<TbOnlineRefundEexit> page, TbOnlineRefundEexit tbOnlineRefundEexit) {
        tbOnlineRefundEexit.setDataScope(SystemUtils.newInstance().findDataScope("tbOnlineRefundEexit:page"));
        page.initOrder();
        PageVo<TbOnlineRefundEexit> eexitIPage = this.mapper.findPage(page,tbOnlineRefundEexit);
        convertData(eexitIPage.getRecords());
        eexitIPage.getDataMap().put("provinceId",merchantConfig.getProvinceId());
        return eexitIPage;
    }

    @Override
    public TbOnlineRefundEexit findUniqueByProperty(String tbRefundApplySysId) {
        TbOnlineRefundEexit eexit = this.mapper.findUniqueByProperty("tbRefundApplySysId", tbRefundApplySysId);
        Map<String, String> merchantInfo = merchantInfo();
        decorate(eexit,merchantInfo);
        return eexit;
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbOnlineRefundEexit tbOnlineRefundEexit) {
        List<TbOnlineRefundEexit> list = this.findList(tbOnlineRefundEexit);
        convertData(list);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行退费流水查询-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, TbOnlineRefundEexit.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行退费流水查询", "银行退费流水查询"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
      * 导出模板
      */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行退费流水查询模板");
        mv.addObject(NormalExcelConstants.CLASS, TbOnlineRefundEexit.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行退费流水查询", "银行退费流水查询"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    /**
     * 获取所有商户信息
     * @return
     */
    private Map<String,String> merchantInfo() {
        ShiroUser principal = ShiroSecurityUtil.getPrincipal();
        String userId = principal.getId();
        Boolean isSuperUser = principal.getIsSuperUser();
        Map<String, String> merchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        return merchantByUserId;
    }

    /**
     * 数据转换
     * @param records
     */
    private void convertData(List<TbOnlineRefundEexit> records) {
        Map<String, String> merchantInfo = merchantInfo();
        Iterator<TbOnlineRefundEexit> iterator = records.iterator();
        while (iterator.hasNext()) {
            decorate(iterator.next(), merchantInfo);
        }
    }

    /**
     *
     * @param eexit
     * @param merchantInfo
     */
    private void decorate(TbOnlineRefundEexit eexit, Map<String, String> merchantInfo) {
        String merchantGroupId = eexit.getMerchantGroupId();
        String merchantId = eexit.getMerchantId();
        String siteId = eexit.getSiteId();
        String merchantGroupIdName = merchantInfo.get(merchantGroupId);
        String merchantIdName = merchantInfo.get(merchantId);
        String siteIdName = merchantInfo.get(siteId);
        eexit.setMerchantGroupIdName(StringUtils.isEmpty(merchantGroupIdName) ? "未知拓展方" : merchantGroupIdName);
        eexit.setMerchantIdName(StringUtils.isEmpty(merchantIdName) ? "未知运营方" : merchantIdName);
        eexit.setSiteIdName(StringUtils.isEmpty(siteIdName) ? "未知场景" : siteIdName);
        eexit.setServiceTypeName(ServiceTypeEnum.getTitleByValue(eexit.getServiceType()));
        eexit.setRefundTypeName(RefundTypeEnum.getTitleByValue(eexit.getRefundType()));
        eexit.setRefundStateName(RefundStateEnum.getTitleByValue(eexit.getRefundState()));
        eexit.setVehicleId(eexit.getVehiclePlate().concat("_").concat(VehicleColor.getColorDescByColorNumber(eexit.getVehicleColor())));
    }
}