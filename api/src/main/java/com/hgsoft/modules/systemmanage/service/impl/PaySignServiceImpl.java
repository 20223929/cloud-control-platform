package com.hgsoft.modules.systemmanage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.modules.config.MerchantConfig;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.systemmanage.entity.shanxi.PaySign;
import com.hgsoft.modules.systemmanage.enums.PaySignManageEnum;
import com.hgsoft.modules.systemmanage.mapper.PaySignMapper;
import com.hgsoft.modules.systemmanage.service.PaySignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 签约管理服务实现层
 * Created by 吴鉴武 on 2021/6/4 15:11
 */
@Service
@RequiredArgsConstructor
public class PaySignServiceImpl implements PaySignService {

    private final PaySignMapper mapper;
    private final MerchantConfig config;
    @Resource(name = "businessRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public IPage<PaySign> findPage(IPage<PaySign> page, PaySign paySign) {
        IPage<PaySign> iPage;
        if(config.getProvinceId().equals("51")){
            iPage = mapper.findPageSC(page,paySign);
        }else{
            iPage = mapper.findPage(page, paySign);
        }
        if(CollUtil.isEmpty(iPage.getRecords())) return iPage;
        iPage.getRecords().replaceAll(vo->{
            dataFormat(vo);
            return vo;
        });
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String editStatus(Long sysId,Integer status,String vehicleNumber) {
        try {
            if(config.getProvinceId().equals("51")){
                mapper.updateStatusSC(sysId, status);
            }else{
                mapper.updateStatus(sysId, status);
            }
            redisTemplate.boundHashOps("paymentgatewayjob:stateAndSignAgreement").put("use:state:" + vehicleNumber,status);
            return "状态变更成功";
        }catch (Exception e){
            return "状态变更失败"+ ExceptionUtil.getMessage(e);
        }
    }

    @Override
    public ModelAndView exportExcel(PaySign paySign) {
        List<PaySign> list;
        if(config.getProvinceId().equals("51")){
            list = mapper.findListSC(paySign);
        }else{
            list = mapper.findList(paySign);
        }
        list.replaceAll(vo->{
            dataFormat(vo);
            return vo;
        });
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "二次签约名单管理-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, PaySign.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("二次签约名单管理", "二次签约名单管理"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
     * 数据格式化
     * @param paySign
     */
    private void dataFormat(PaySign paySign){
        paySign.setBankCardTypeDesc(config.getProvinceId().equals("14") || config.getProvinceId().equals("51")
                ? PaySignManageEnum.getDescByValue(PaySignManageEnum.ZhihuifuCardLogo.values(),paySign.getBankCardType())
                : PaySignManageEnum.getDescByValue(PaySignManageEnum.IcbcCardLogo.values(),paySign.getBankCardType()));
        paySign.setVehicleColorDesc(VehicleColor.getColorDescByColorNumber(paySign.getVehicleColor()));
        paySign.setVehicleClassDesc(PaySignManageEnum.getDescByValue(PaySignManageEnum.VehicleType.values(),paySign.getVehicleClass()));
        paySign.setStatusDesc(PaySignManageEnum.getDescByValue(PaySignManageEnum.status.values(),paySign.getStatus()));
        paySign.setSignStateDesc(config.getProvinceId().equals("51") ? PaySignManageEnum.getDescByValue(PaySignManageEnum.SignStateSC.values(),paySign.getSignState()) : PaySignManageEnum.getDescByValue(PaySignManageEnum.SignState.values(),paySign.getSignState()));
    }
}
