package com.hgsoft.modules.monitor.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.hgsoft.modules.monitor.entity.TbAbnormalAlarm;
import com.hgsoft.modules.monitor.enums.AlarmLevelEnum;
import com.hgsoft.modules.monitor.enums.AlarmTypeEnum;
import com.hgsoft.modules.monitor.enums.DealStateEnum;
import com.hgsoft.modules.monitor.mapper.TbAbnormalAlarmMapper;
import com.hgsoft.modules.monitor.service.TbAbnormalAlarmService;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;

import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;

import com.hgsoft.ecip.web.util.SystemUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

import com.hgsoft.ecip.framework.util.IdWorkerInit;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.ExcelImportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.excel.entity.ImportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.apache.commons.lang3.StringUtils;

import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;



/**
 * 异常报警管理ServiceImpl
 * @author 雷新辉
 * @version 2021-07-06 03:40:21
 */

@Service("com.hgsoft.modules.monitor.service.TbAbnormalAlarmService")
public class TbAbnormalAlarmServiceImpl extends CrudServiceImpl<TbAbnormalAlarmMapper, TbAbnormalAlarm> implements TbAbnormalAlarmService {

    private final String[] tipString = {
        "sysId(【主键sysId】): 可以不填写主键sysId；",
        "sysId(【系统编号】): 必填",
        "alarmType(【报警类型，数据字典配置，1-黑名单异常、2-对账异常、3-收费异常、4-服务器异常、5-网络异常、6-应用异常、7-车道监控异常】): 必填",
        "alarmTime(【报警时间】): 必填",
        "alarmLevel(【报警级别，1-一般、2-严重】): 必填",
        "alarmInfo(【报警信息】): 必填",
        "dealState(【处理状态，0-未处理、1-已处理】): 必填",
        "dealTime(【处理时间】): 非必填",
        "dealUserId(【处理人id】): 非必填",
        "createTime(【创建时间】): 必填",
        "updateTime(【更新时间】): 必填",
        "remarks(【备注信息】): 非必填"
    };

    /**
     * 分页查询
     * @param page
     * @param tbAbnormalAlarm
     * @return
     */
    @Override
    public IPage<TbAbnormalAlarm> tbAbnormalAlarmPage(Page<TbAbnormalAlarm> page, TbAbnormalAlarm tbAbnormalAlarm) {
        tbAbnormalAlarm.setDataScope(SystemUtils.newInstance().findDataScope("tbAbnormalAlarm:page"));
        page.initOrder();
        IPage<TbAbnormalAlarm> alarmIPage = this.findPage(page, tbAbnormalAlarm);
        List<TbAbnormalAlarm> records = alarmIPage.getRecords();
        convert(records);
        return alarmIPage;
    }

    @Override
    public TbAbnormalAlarm getByPrimaryKey(String sysId) {
        return this.getById(sysId);
    }


    @Override
    @Transactional
    public void saveTbAbnormalAlarm(TbAbnormalAlarm tbAbnormalAlarm) {
        tbAbnormalAlarm.setSysId(IdWorkerInit.generateIdStr());

        tbAbnormalAlarm.preInsert();
        this.saveEntity(tbAbnormalAlarm);
    }



    /**
     * 批量删除(物理删除)
     */
    @Transactional
    @Override
    public void deleteByPrimaryKey(List<String> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        this.removeByIds(dataList);
    }

    /**
     * 导入数据
     */
    @Override
    @Transactional
    public List<String> importExcel(MultipartFile file, boolean isNewPk, String strategy) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        List<TbAbnormalAlarm> list = ExcelImportUtil.importExcel(file.getInputStream(), TbAbnormalAlarm.class, params);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<TbAbnormalAlarm> saveList = new ArrayList<>();
        List<TbAbnormalAlarm> updateList = new ArrayList<>();
        List<String> dataIds = list.stream().map(TbAbnormalAlarm::getSysId).collect(Collectors.toList());
        List<String> existIds = new ArrayList<>();
        if (!isNewPk) {
            LambdaQueryWrapper<TbAbnormalAlarm> queryWrapper = new LambdaQueryWrapper<TbAbnormalAlarm>().select(TbAbnormalAlarm::getSysId);
            queryWrapper.in(TbAbnormalAlarm::getSysId, dataIds);
            existIds = this.list(queryWrapper).stream().map(TbAbnormalAlarm::getSysId).collect(Collectors.toList());
            dataIds.removeAll(existIds);    // 筛选出存在数据库Id，余下则为不存在数据库ids
        }
        for(TbAbnormalAlarm item: list) {
            // 主键策略为使用新增主键 或者 主键值为空，则认为是新增
            if (isNewPk || StringUtils.isBlank(item.getSysId())) {
                item.setSysId(IdWorkerInit.generateIdStr());
                item.preInsert();
                saveList.add(item);
            } else {    // isNewPk:false, 主键值不为空, dataIds判断是否为数据库不存在数据, 若是不存在, 则为新增数据, 且id以excel导入为准
                if (dataIds.contains(item.getSysId())) {
                    ShiroUser shiroUser = new ShiroUser();
                    shiroUser.setId(ShiroSecurityUtil.userId());
                    saveList.add(item);
                } else if (strategy.equals("update")) {
                    if (existIds.contains(item.getSysId())) {
                        item.preUpdate();
                        updateList.add(item);
                    }
                }
            }
        }
        if (!saveList.isEmpty()) {
            this.saveBatch(saveList);
        }
        if (!updateList.isEmpty()) {
            this.updateBatchById(updateList);
        }
        return list.stream().map(TbAbnormalAlarm::getSysId).collect(Collectors.toList());
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbAbnormalAlarm tbAbnormalAlarm) {
        List<TbAbnormalAlarm> list = this.findList(tbAbnormalAlarm);
        convert(list);

        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "异常报警管理-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, TbAbnormalAlarm.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("异常报警管理", "异常报警管理"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
      * 导出模板
      */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "异常报警管理模板");
        mv.addObject(NormalExcelConstants.CLASS, TbAbnormalAlarm.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("异常报警管理", "异常报警管理"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    /**
     * 批量处理
     */
    @Transactional
    @Override
    public void dealByPrimaryKey(List<String> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        List<TbAbnormalAlarm> list = new ArrayList<>();
        DateTime now = DateUtil.date();

        dataList.listIterator().forEachRemaining(c -> {
            TbAbnormalAlarm alarm = new TbAbnormalAlarm();
            alarm.setSysId(c).setDealState(DealStateEnum.HAS_DEAL.getValue())
                    .setDealTime(now).setDealUserId(ShiroSecurityUtil.userId());
            list.add(alarm);
        });
        this.updateBatchById(list);
    }

    private List<TbAbnormalAlarm> convert(List<TbAbnormalAlarm> list) {
        list.listIterator().forEachRemaining(c -> {
            c.setAlarmLevelName(AlarmLevelEnum.getTitleByValue(c.getAlarmLevel()))
                    .setAlarmTypeName(AlarmTypeEnum.getTitleByValue(c.getAlarmType()))
                    .setDealStateName(DealStateEnum.getTitleByValue(c.getDealState()));
        });
        return list;
    }
}