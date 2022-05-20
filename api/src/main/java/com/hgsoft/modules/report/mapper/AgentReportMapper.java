package com.hgsoft.modules.report.mapper;

import com.hgsoft.modules.report.entity.AgentReport;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 代理商收益报表MAPPER接口
 * @author
 * @version 2022-05-06 23:07:31
 */
@Repository("com.hgsoft.modules.report.mapper.AgentReportMapper")
public interface AgentReportMapper extends DataMapper<AgentReport> {

    PageVo<AgentReport> findPageByCondition(PageVo<AgentReport> page, @Param("entity") AgentReport agentReport, @Param("siteIds") List<String> siteIds);

    List<AgentReport> findListByCondition(@Param("entity") AgentReport agentReport, @Param("siteIds") List<String> siteIds);

    AgentReport getSum(@Param("entity") AgentReport agentReport, @Param("siteIds") List<String> siteIds);
}