package com.hgsoft.modules.settlementreject.service.impl;

import cn.hutool.core.date.DateUtil;
import com.hgsoft.modules.querymanage.enums.TransStatusEnum;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.settlementreject.entity.SettlementReject;
import com.hgsoft.modules.settlementreject.mapper.SettlementRejectMapper;
import com.hgsoft.modules.settlementreject.service.SettlementRejectService;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.web.util.SystemUtils;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;



/**
 * 退费详情表ServiceImpl
 * @author 郑裕强
 * @version 2022-05-08 03:40:27
 */

@Service("com.hgsoft.modules.settlementreject.service.SettlementRejectService")
public class SettlementRejectServiceImpl extends CrudServiceImpl<SettlementRejectMapper, SettlementReject> implements SettlementRejectService {

    /**
     * 分页查询
     * @param page
     * @param settlementReject
     * @return
     */
    @Override
    public PageVo<SettlementReject> settlementRejectPage(PageVo<SettlementReject> page, SettlementReject settlementReject) {
        settlementReject.setDataScope(SystemUtils.newInstance().findDataScope("settlementReject:page"));
        page.initOrder();
        PageVo<SettlementReject> iPage = (PageVo<SettlementReject>)this.findPage(page, settlementReject);
        //数据修饰
        iPage.getRecords().replaceAll(vo -> {
            if(vo.getFee() != null) vo.setFee(vo.getFee().divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            if(vo.getTransTime() != null) vo.setTransDate(DateUtil.format(vo.getTransTime(), "yyyy-MM-dd HH:mm"));
            vo.setVehicleColorTxt(VehicleColor.getColorDescByColorNumber(vo.getVehicleColor()));
            vo.setTransStaTxt(TransStatusEnum.getTitleByValue(vo.getTransSta()));
            return vo;
        });
        return iPage;
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, SettlementReject settlementReject) {
        List<SettlementReject> list = this.findList(settlementReject);
        //数据修饰
        list.replaceAll(vo -> {
            if(vo.getFee() != null) vo.setFee(vo.getFee().divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            if(vo.getTransTime() != null) vo.setTransDate(DateUtil.format(vo.getTransTime(), "yyyy-MM-dd HH:mm"));
            vo.setVehicleColorTxt(VehicleColor.getColorDescByColorNumber(vo.getVehicleColor()));
            vo.setTransStaTxt(TransStatusEnum.getTitleByValue(vo.getTransSta()));
            return vo;
        });
        //转换车牌颜色
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "预支抵扣详情表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, SettlementReject.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("预支抵扣详情表", "预支抵扣详情表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }
}