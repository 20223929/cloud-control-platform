package com.hgsoft.modules.report.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.ClearAccountReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 省中心清分对账报表MAPPER接口
 * @author 吴鉴武
 * @date 2021-04-19 23:29:04
 */
@Repository("com.hgsoft.modules.merchant.mapper.ClearAccountReportMapper")
public interface ClearAccountReportMapper extends DataMapper<ClearAccountReport> {

    IPage<ClearAccountReport> findPage(IPage<ClearAccountReport> iPage, @Param("entity") ClearAccountReport clearAccountReport,@Param("dimension") String dimension);

    /**
     * 根据条件查询汇总信息
     * @param clearAccountReport
     * @return
     */
    ClearAccountReport getSum(@Param("entity") ClearAccountReport clearAccountReport,@Param("dimension") String dimension);

    /**
     * 根据条件查询列表
     * @param clearAccountReport
     * @return
     */
    List<ClearAccountReport> findListByCondition(@Param("entity") ClearAccountReport clearAccountReport,@Param("dimension") String dimension);
}