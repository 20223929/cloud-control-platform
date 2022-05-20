package com.hgsoft.modules.monitor.mapper;

import com.hgsoft.modules.monitor.entity.TbAbnormalAlarm;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.springframework.stereotype.Repository;

/**
 * 异常报警管理MAPPER接口
 * @author 雷新辉
 * @version 2021-07-06 03:40:21
 */
@Repository("com.hgsoft.modules.monitor.mapper.TbAbnormalAlarmMapper")
public interface TbAbnormalAlarmMapper extends DataMapper<TbAbnormalAlarm> {

}