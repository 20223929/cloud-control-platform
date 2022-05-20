package com.hgsoft.modules.monitor.service;

import com.hgsoft.modules.monitor.entity.TbAbnormalAlarm;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.ecip.framework.common.response.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 异常报警管理Service
 * @author 雷新辉
 * @version 2021-07-06 03:40:21
 */
public interface TbAbnormalAlarmService extends CrudService<TbAbnormalAlarm> {

    IPage<TbAbnormalAlarm> tbAbnormalAlarmPage(Page<TbAbnormalAlarm> page, TbAbnormalAlarm tbAbnormalAlarm);

    TbAbnormalAlarm getByPrimaryKey(String sysId);

    void saveTbAbnormalAlarm(TbAbnormalAlarm tbAbnormalAlarm);
    
    /**
     * 批量删除(物理删除)
     */
    void deleteByPrimaryKey(List<String> dataList);

    
    /**
     * 导入
     */
    List<String> importExcel(MultipartFile file, boolean isNewPk, String strategy) throws Exception;

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbAbnormalAlarm tbAbnormalAlarm);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    /**
     * 批量处理
     * @param dataList
     */
    void dealByPrimaryKey(List<String> dataList);
}