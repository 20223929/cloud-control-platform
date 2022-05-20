package com.hgsoft.modules.querymanage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.modules.querymanage.entity.ParkEexit;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 停车场流水查询MAPPER接口
 * @author Andy
 * @version 2021-04-16 22:34:19
 */
@Repository("com.hgsoft.modules.querymanage.mapper.ParkEexitMapper")
public interface ParkEexitMapper extends DataMapper<ParkEexit> {

    /**
     * 根据条件查询停车场流水列表
     * @param parkEexit
     * @return
     */
    @Override
    List<ParkEexit> findList(@Param("entity") ParkEexit parkEexit);

    /**
     * 根据条件查询单挑停车场流水
     * @param parkEexit
     * @return
     */
    ParkEexit findByCondition(@Param("entity") ParkEexit parkEexit);
}