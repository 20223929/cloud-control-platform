package com.hgsoft.modules.api.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.api.entity.VehicleCountVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.api.mapper.VehicleCountMapper")
public interface VehicleCountMapper extends DataMapper<VehicleCountVo> {

    List<VehicleCountVo> findByTime(VehicleCountVo vo);
}
