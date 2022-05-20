package com.hgsoft.modules.report.mapper;

import com.hgsoft.modules.report.entity.ServiceRecvReport;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 服务方应收报表MAPPER接口
 * @author 雷新辉
 * @version 2022-04-28 05:44:26
 */
@Repository("com.hgsoft.modules.report.mapper.ServiceRecvReportMapper")
public interface ServiceRecvReportMapper extends DataMapper<ServiceRecvReport> {

    ServiceRecvReport getSum(@Param("entity") ServiceRecvReport serviceRecvReport);
}