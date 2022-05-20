package com.hgsoft.modules.querymanage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.querymanage.entity.ParkEexit;
import com.hgsoft.modules.querymanage.enums.ParkEexitStatusEnum;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.querymanage.mapper.ParkEexitMapper;
import com.hgsoft.modules.querymanage.service.ParkEexitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * 停车场流水查询ServiceImpl
 * @author Andy
 * @version 2021-04-16 22:34:19
 */

@Service("com.hgsoft.modules.querymanage.service.ParkEexitService")
public class ParkEexitServiceImpl extends CrudServiceImpl<ParkEexitMapper, ParkEexit> implements ParkEexitService {
    @Autowired
    private MerchantManageService merchantManageService;

    /**
     * 分页查询
     * @param page
     * @param parkEexit
     * @return
     */
    @Override
    public IPage<ParkEexit> parkEexitPage(Page<ParkEexit> page, ParkEexit parkEexit) {
        IPage<ParkEexit> parkEexitIPage = this.mapper.findPage(page, parkEexit);
        if(CollUtil.isEmpty(parkEexitIPage.getRecords())) return parkEexitIPage;
        convertData(parkEexitIPage.getRecords());
        return parkEexitIPage;
    }

    @Override
    public ParkEexit findUniqueByProperty(ParkEexit params) {
        ParkEexit parkEexit = this.mapper.findByCondition(params);
        Map<String, String> merchantInfo = merchantInfo();
        decorate(parkEexit,merchantInfo);
        return parkEexit;
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, ParkEexit parkEexit) {
        List<ParkEexit> list = this.mapper.findList(parkEexit);
        if(CollUtil.isNotEmpty(list))convertData(list);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "停车场流水查询-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, ParkEexit.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("停车场流水查询", "停车场流水查询"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
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
    private void convertData(List<ParkEexit> records) {
        Map<String, String> merchantInfo = merchantInfo();
        Iterator<ParkEexit> iterator = records.iterator();
        while (iterator.hasNext()) {
            decorate(iterator.next(), merchantInfo);
        }
    }

    /**
     *
     * @param parkEexit
     * @param merchantInfo
     */
    private void decorate(ParkEexit parkEexit, Map<String, String> merchantInfo) {
        String merchantGroupId = parkEexit.getMerchantGroupId();
        String merchantId = parkEexit.getMerchantId();
        String siteId = parkEexit.getSiteId();
        String merchantGroupIdName = merchantInfo.get(merchantGroupId);
        String merchantIdName = merchantInfo.get(merchantId);
        String siteIdName = merchantInfo.get(siteId);
        parkEexit.setMerchantGroupIdName(StringUtils.isEmpty(merchantGroupIdName) ? "未知拓展方" : merchantGroupIdName);
        parkEexit.setMerchantIdName(StringUtils.isEmpty(merchantIdName) ? "未知运营方" : merchantIdName);
        parkEexit.setSiteIdName(StringUtils.isEmpty(siteIdName) ? "未知场景" : siteIdName);
        parkEexit.setStatusName(ParkEexitStatusEnum.getTitleByValue(parkEexit.getStatus()));
        parkEexit.setVehicleColorName(VehicleColor.getColorDescByColorNumber(parkEexit.getVehicleColor()));
        parkEexit.setModelTypeName(parkEexit.getModelType().intValue() == 1 ? "联机" : "脱机");
        long diffMinutes = DateUtil.between(DateUtil.parse(parkEexit.getExTime(), DatePattern.NORM_DATETIME_PATTERN), DateUtil.parse(parkEexit.getEnTime(), DatePattern.NORM_DATETIME_PATTERN),DateUnit.MINUTE);
        parkEexit.setParkHours(StrUtil.format("{}小时{}分钟",diffMinutes / 60,diffMinutes % 60));
    }
}