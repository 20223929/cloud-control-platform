package com.hgsoft.modules.monitor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.modules.merchant.entity.TbMerchant;
import com.hgsoft.modules.merchant.service.SysUserMerchantService;
import com.hgsoft.modules.merchant.service.TbMerchantService;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.monitor.entity.ChargeExcepion;
import com.hgsoft.modules.monitor.mapper.ChargeExcepionMapper;
import com.hgsoft.modules.monitor.service.ChargeExcepionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 收费异常管理ServiceImpl
 * @author 吴鉴武
 * @version 2021-04-21 22:35:10
 */

@Service("com.hgsoft.modules.monitor.service.ChargeExcepionService")
@RequiredArgsConstructor
public class ChargeExcepionServiceImpl extends CrudServiceImpl<ChargeExcepionMapper, ChargeExcepion> implements ChargeExcepionService {
    private final MerchantManageService merchantManageService;
    /**
     * 分页查询
     * @param page
     * @param chargeExcepion
     * @return
     */
    @Override
    public IPage<ChargeExcepion> chargeExcepionPage(Page<ChargeExcepion> page, ChargeExcepion chargeExcepion) {
        page.initOrder();
        IPage<ChargeExcepion> ipage = this.findPage(page, chargeExcepion);
        if(CollUtil.isEmpty(ipage.getRecords())) return ipage;
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(chargeExcepion.getUserMerchantParam() == null ? null : chargeExcepion.getUserMerchantParam().getSearchIds(),ShiroSecurityUtil.isSuperUser());
        ipage.getRecords().replaceAll(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            return vo;
        });
        return ipage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirm(List<ChargeExcepion> list) {
        this.baseMapper.confirmEtc(list,list.get(0));
        this.baseMapper.confirmOnline(list,list.get(0));
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, ChargeExcepion chargeExcepion) {
        List<ChargeExcepion> list = this.baseMapper.findListByCondition(chargeExcepion);
        if(CollUtil.isNotEmpty(list)){
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(chargeExcepion.getUserMerchantParam() == null ? null : chargeExcepion.getUserMerchantParam().getSearchIds(),ShiroSecurityUtil.isSuperUser());
            BigDecimal oneHundred = new BigDecimal(100);
            list.replaceAll(vo->{
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                vo.setEtcAmount(vo.getEtcAmount().divide(oneHundred).setScale(2,BigDecimal.ROUND_HALF_UP));
                vo.setRealEtcAmount(vo.getRealEtcAmount().divide(oneHundred).setScale(2,BigDecimal.ROUND_HALF_UP));
                vo.setOnlineAmount(vo.getOnlineAmount().divide(oneHundred).setScale(2,BigDecimal.ROUND_HALF_UP));
                vo.setRealOnlineAmount(vo.getRealOnlineAmount().divide(oneHundred).setScale(2,BigDecimal.ROUND_HALF_UP));
                vo.setTotalEtcAmount(vo.getTotalEtcAmount().divide(oneHundred).setScale(2,BigDecimal.ROUND_HALF_UP));
                vo.setTotalOnlineAmount(vo.getTotalOnlineAmount().divide(oneHundred).setScale(2,BigDecimal.ROUND_HALF_UP));
                return vo;
            });
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "收费异常管理-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, ChargeExcepion.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("收费异常管理", "收费异常管理"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }
}