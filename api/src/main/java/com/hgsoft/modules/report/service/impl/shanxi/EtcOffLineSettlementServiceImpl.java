package com.hgsoft.modules.report.service.impl.shanxi;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.report.entity.shanxi.EtcOffLineSettlement;
import com.hgsoft.modules.report.mapper.shanxi.EtcOffLineSettlementMapper;
import com.hgsoft.modules.report.service.shanxi.EtcOffLineSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EtcOffLineSettlementServiceImpl implements EtcOffLineSettlementService {

    @Autowired
    private EtcOffLineSettlementMapper mapper;

    @Autowired
    private MerchantManageService merchantManageService;

    @Override
    public ModelAndView exportData(HttpServletRequest request, EtcOffLineSettlement s) {
        List<EtcOffLineSettlement> list = mapper.findSearchDataAll(s);
        final Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        list.forEach(c->c.triggerShowState(merchantInfo));
        EtcOffLineSettlement etcOffLineSettlement = mapper.sumAll(s);
        if(etcOffLineSettlement!=null){
            etcOffLineSettlement.triggerShowState(merchantInfo);
            etcOffLineSettlement.setTransDate("合计");
            list.add(etcOffLineSettlement);
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心脱机交易对账-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, EtcOffLineSettlement.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心脱机交易对账", "联网中心脱机交易对账"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "车辆查询模板");
        mv.addObject(NormalExcelConstants.CLASS, EtcOffLineSettlement.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车辆查询表", "车辆查询"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public IPage<EtcOffLineSettlement> findSearchDataPage(Page<EtcOffLineSettlement> page, EtcOffLineSettlement entity) {
        IPage<EtcOffLineSettlement> searchDataPage = mapper.findSearchDataPage(page, entity);
        final Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        List<EtcOffLineSettlement> records = searchDataPage.getRecords();
        records.forEach(c->c.triggerShowState(merchantInfo));
        EtcOffLineSettlement etcOffLineSettlement = mapper.sumAll(entity);
        if(etcOffLineSettlement!=null){
            etcOffLineSettlement.triggerShowState(merchantInfo);
            etcOffLineSettlement.setTransDate("合计");
            records.add(etcOffLineSettlement);
        }
        return searchDataPage;
    }
}
