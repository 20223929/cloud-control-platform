package com.hgsoft.modules.querymanage.service.impl.shanxi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.querymanage.entity.shanxi.GasStationQuery;
import com.hgsoft.modules.querymanage.enums.ParkEexitStatusEnum;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.querymanage.mapper.shanxi.GasStationQueryMapper;
import com.hgsoft.modules.querymanage.service.shanxi.GasStationQueryService;
import com.hgsoft.modules.report.entity.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 加油站流水查询servicempl
 * Created by 吴鉴武 on 2021/5/7 8:54
 */
@Service
@Slf4j
public class GasStationQueryServiceImpl implements GasStationQueryService {

    @Autowired
    private GasStationQueryMapper mapper;
    @Autowired
    private MerchantManageService merchantManageService;

    @Override
    public PageVo<GasStationQuery> findPage(PageVo<GasStationQuery> page, GasStationQuery gasStationQuery) {
        PageVo<GasStationQuery> iPage = mapper.findPage(page, gasStationQuery);
        if (CollUtil.isEmpty(iPage.getRecords())) return iPage;
        Map<String, String> allMerchant = merchantManageService.getAllMerchant(gasStationQuery.getUserMerchantParam() == null ? null : gasStationQuery.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
        iPage.getRecords().replaceAll(vo -> {
            dataFormat(vo, allMerchant);
            return vo;
        });
        return iPage;
    }

    @Override
    public ModelAndView exportExcel(GasStationQuery gasStationQuery) {
        List<GasStationQuery> list = mapper.findListByConditions(gasStationQuery);
        if (CollUtil.isNotEmpty(list)) {
            Map<String, String> allMerchant = merchantManageService.getAllMerchant(gasStationQuery.getUserMerchantParam() == null ? null : gasStationQuery.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
            list.replaceAll(vo -> {
                dataFormat(vo, allMerchant);
                vo.setTradeFee(vo.getTradeFee().divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
                return vo;
            });
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "加油站流水信息-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, GasStationQuery.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("加油站流水信息", "加油站流水信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    @Override
    public GasStationQuery findDataById(GasStationQuery queryParam) {
        GasStationQuery gasStationQuery = mapper.findById(queryParam);
        ShiroUser principal = ShiroSecurityUtil.getPrincipal();
        Map<String, String> allMerchant = merchantManageService.getAllMerchantByUserId(principal.getId(), principal.getIsSuperUser());
        dataFormat(gasStationQuery, allMerchant);
        return gasStationQuery;
    }

    /**
     * 格式化数据
     *
     * @param gasStationQuery
     * @param allMerchant
     */
    private void dataFormat(GasStationQuery gasStationQuery, Map<String, String> allMerchant) {
        gasStationQuery.setVehicleColorDesc(VehicleColor.getColorDescByColorNumber(gasStationQuery.getVehicleColor()));
        gasStationQuery.setMerchantGroupName(allMerchant.getOrDefault(gasStationQuery.getMerchantGroupId(), "未知拓展方"));
        gasStationQuery.setMerchantName(allMerchant.getOrDefault(gasStationQuery.getMerchantId(), "未知运营方"));
        gasStationQuery.setSiteName(allMerchant.getOrDefault(gasStationQuery.getSiteId(), "未知场景"));
        gasStationQuery.setStatusName(ParkEexitStatusEnum.getTitleByValue(gasStationQuery.getStatus()));
        gasStationQuery.setModelTypeName(gasStationQuery.getModelType().intValue() == 1 ? "联机" : "脱机");
        gasStationQuery.getDetailInfo().setFuelVolume(gasStationQuery.getDetailInfo().getFuelVolume().divide(BigDecimal.valueOf(1000)));
    }
}
