package com.hgsoft.modules.report.mapper.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.shanxi.EtcPayReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联网中心扣款报表mapper
 * Created by 吴鉴武 on 2021/5/6 13:47
 */
@Repository
public interface EtcPayReportMapper extends DataMapper<EtcPayReport> {

    /**
     * 分页查询
     * @param page
     * @param entity
     * @return
     */
    PageVo<EtcPayReport> findPage(IPage<EtcPayReport> page, @Param("entity") EtcPayReport entity,@Param("statisticsDimension") String statisticsDimension);

    /**
     * 查询汇总信息
     * @param entity
     * @return
     */
    EtcPayReport getSum(@Param("entity") EtcPayReport entity);

    /**
     * 根据条件查询列表
     * @param entity
     * @return
     */
    List<EtcPayReport> findListByCondition(@Param("entity") EtcPayReport entity,@Param("statisticsDimension") String statisticsDimension);
}
