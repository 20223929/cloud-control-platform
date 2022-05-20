package com.hgsoft.modules.report.mapper.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.shanxi.EtcRemittanceReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联网中心汇款报表MAPPER接口
 * @author 吴鉴武
 * @version 2021-05-06 03:55:57
 */
@Repository("com.hgsoft.modules.report.mapper.EtcRemittanceReportMapper")
public interface EtcRemittanceReportMapper extends DataMapper<EtcRemittanceReport> {

    /**
     * 分页查询联网中心汇款报表信息
     * @param page
     * @param entity
     * @return
     */
    PageVo<EtcRemittanceReport> findPage(IPage<EtcRemittanceReport> page, @Param("entity") EtcRemittanceReport entity,@Param("statisticsDimension") String statisticsDimension);

    /**
     * 获取汇总信息
     * @param entity
     * @return
     */
    EtcRemittanceReport getSum(@Param("entity") EtcRemittanceReport entity);

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<EtcRemittanceReport> findListByCondition(@Param("entity") EtcRemittanceReport entity,@Param("statisticsDimension") String statisticsDimension);
}