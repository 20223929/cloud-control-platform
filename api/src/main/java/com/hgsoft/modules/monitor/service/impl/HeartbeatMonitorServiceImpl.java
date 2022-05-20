package com.hgsoft.modules.monitor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.merchant.enums.ServiceTypeEnum;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.merchantcommon.NodeLevelEnum;
import com.hgsoft.modules.monitor.entity.HeartbeatMonitor;
import com.hgsoft.modules.monitor.enums.MonitorEnum;
import com.hgsoft.modules.monitor.mapper.HeartbeatMonitorMapper;
import com.hgsoft.modules.monitor.service.HeartbeatMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 车道监控ServiceImpl
 * @author 吴鉴武
 * @version 2021-06-01 22:47:13
 */
@Slf4j
@Service("com.hgsoft.modules.monitor.service.HeartbeatMonitorService")
public class HeartbeatMonitorServiceImpl extends CrudServiceImpl<HeartbeatMonitorMapper, HeartbeatMonitor> implements HeartbeatMonitorService {

    @Autowired
    private MerchantManageService merchantManageService;
    /**
     * 分页查询
     * @param page
     * @param heartbeatMonitor
     * @return
     */
    @Override
    public IPage<HeartbeatMonitor> heartbeatMonitorPage(Page<HeartbeatMonitor> page, HeartbeatMonitor heartbeatMonitor) {
        IPage<HeartbeatMonitor> iPage = this.mapper.findPage(page, heartbeatMonitor);
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        Map<String, String> allMerchant = merchantManageService.getAllMerchant(heartbeatMonitor.getUserMerchantParam() == null ? null : heartbeatMonitor.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
        iPage.getRecords().replaceAll(vo->{
            dataFormat(vo,allMerchant);
            return vo;
        });
        return iPage;
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletResponse response, HeartbeatMonitor heartbeatMonitor) {
        List<HeartbeatMonitor> list = this.mapper.findList(heartbeatMonitor);
        if(CollUtil.isNotEmpty(list)){
            Map<String, String> allMerchant = merchantManageService.getAllMerchant(heartbeatMonitor.getUserMerchantParam() == null ? null : heartbeatMonitor.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
            list.replaceAll(vo->{
                dataFormat(vo,allMerchant);
                return vo;
            });
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "车道监控-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, HeartbeatMonitor.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车道监控", "车道监控"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
     * 数据格式化
     * @param vo
     * @param allMerchant
     */
    private void dataFormat(HeartbeatMonitor vo,Map<String, String> allMerchant){
        vo.setMerchantGroupName(allMerchant.getOrDefault(vo.getSiteId().substring(0, NodeLevelEnum.MERCHANT_GROUP.getIdLength()),"未知拓展方"));
        vo.setMerchantName(allMerchant.getOrDefault(vo.getSiteId().substring(0, NodeLevelEnum.MERCHANT.getIdLength()),"未知运营方"));
        vo.setSiteName(allMerchant.getOrDefault(vo.getSiteId().substring(0, NodeLevelEnum.SITE.getIdLength()),"未知场景"));
        vo.setSiteSoftState(MonitorEnum.getNameByValue(MonitorEnum.SiteSoftState.values(),vo.getSiteSoftState()));
        vo.setAntennaState(MonitorEnum.getNameByValue(MonitorEnum.AntennaState.values(),vo.getAntennaState()));
        vo.setMaxTransFeeConfigName(MonitorEnum.getNameByValue(MonitorEnum.MaxTransFeeConfig.values(),vo.getMaxTransFeeConfig().toString()));
        vo.setServiceTypeName(ServiceTypeEnum.getTitleByValue(vo.getServiceType()));
        if(StrUtil.isNotBlank(vo.getCardBlacklistVersion())){
            String[] cardVersions = vo.getCardBlacklistVersion().split("\\|");
            vo.setCardBlackAllVersion(cardVersions[0]);
            vo.setCardBlackAddVersion(cardVersions[1]);
        }
        if(StrUtil.isNotBlank(vo.getObuBlackelistVersion())){
            String[] obuVersions = vo.getObuBlackelistVersion().split("\\|");
            vo.setObuBlackAllVersion(obuVersions[0]);
            vo.setObuBlackAddVersion(obuVersions[1]);
        }
    }
}