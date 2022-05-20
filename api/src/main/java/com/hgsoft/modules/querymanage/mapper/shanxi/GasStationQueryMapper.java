package com.hgsoft.modules.querymanage.mapper.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.querymanage.entity.shanxi.GasStationQuery;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加油站流水查询MAPPER接口
 *
 * @author 吴鉴武
 * @version 2021-05-06 05:11:48
 */
@Repository("com.hgsoft.modules.querymanage.mapper.GasStationQueryMapper")
public interface GasStationQueryMapper extends DataMapper<GasStationQuery> {

    /**
     * 分页查询加油站流水
     *
     * @param page
     * @param entity
     * @return
     */
    @Override
    PageVo<GasStationQuery> findPage(IPage<GasStationQuery> page, @Param("entity") GasStationQuery entity);

    /**
     * 查找加油站流水列表
     *
     * @param entity
     * @return
     */
    List<GasStationQuery> findListByConditions(@Param("entity") GasStationQuery entity);

    /**
     * 通过交易流水号查询加油站流水信息
     *
     * @param gasStationQuery
     * @return
     */
    GasStationQuery findById(@Param("entity") GasStationQuery gasStationQuery);
}
