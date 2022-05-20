package com.hgsoft.modules.report.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.SignStatisticReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 二次签约统计报表MAPPER接口
 * @author 吴鉴武
 * @version 2021-04-20 22:01:36
 */
@Repository("com.hgsoft.modules.report.mapper.SignStatisticReportMapper")
public interface SignStatisticReportMapper extends DataMapper<SignStatisticReport> {

    /**
     * 根据条件查询列表
     * @param signStatisticReport
     * @return
     */
    List<SignStatisticReport> findListByCondition(@Param("entity") SignStatisticReport signStatisticReport);

    /**
     * 根据条件查询汇总信息
     * @param signStatisticReport
     * @return
     */
    SignStatisticReport getSum(@Param("entity") SignStatisticReport signStatisticReport);
}